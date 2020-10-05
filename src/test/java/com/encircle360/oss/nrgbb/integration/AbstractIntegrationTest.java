package com.encircle360.oss.nrgbb.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.encircle360.oss.nrgbb.dto.author.AuthorDTO;
import com.encircle360.oss.nrgbb.dto.author.CreateUpdateAuthorDTO;
import com.encircle360.oss.nrgbb.dto.category.CategoryDTO;
import com.encircle360.oss.nrgbb.dto.category.CreateUpdateCategoryDTO;
import com.encircle360.oss.nrgbb.dto.post.CreatePostDTO;
import com.encircle360.oss.nrgbb.dto.post.PostDTO;
import com.encircle360.oss.nrgbb.dto.thread.CreateThreadDTO;
import com.encircle360.oss.nrgbb.dto.thread.ThreadDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractIntegrationTest {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String oauth2IssuerUri;

    @Value("${spring.application.name}")
    private String clientId;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    private String token;

    @BeforeEach
    protected void setToken() {
        String profile =  System.getenv("SPRING_PROFILES_ACTIVE");
        if (profile == null || !profile.equalsIgnoreCase("insecure")) {
            getJwtToken("test", "test");
        } else {
            token = "";
        }
    }

    public void getJwtToken(String username, String password) {
        String url = oauth2IssuerUri.concat("/protocol/openid-connect/token");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("username", username);
        params.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<AccessToken> response = new RestTemplate().postForEntity(url, request, AccessToken.class);

        assertNotNull(response);
        assertNotNull(response.getBody());

        String accessToken = response.getBody().accessToken;
        assertNotNull(accessToken);
        assertFalse(accessToken.isBlank());

        this.token = "Bearer " + accessToken;
    }

    private static class AccessToken {
        @JsonProperty("access_token")
        public String accessToken;
    }

    protected <T> MvcResult post(String url, T object, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(object)))
            .andExpect(status)
            .andReturn();
    }

    protected <T> MvcResult put(String url, T object, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(url)
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(object)))
            .andExpect(status)
            .andReturn();
    }

    protected MvcResult emptyPost(String url, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
            .header("Authorization", token))
            .andExpect(status)
            .andReturn();
    }

    protected MvcResult get(String url, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url)
            .header("Authorization", token))
            .andExpect(status)
            .andReturn();
    }

    protected MvcResult delete(String url, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(url)
            .header("Authorization", token))
            .andExpect(status)
            .andReturn();
    }

    protected <T> T resultToObject(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }

    protected AuthorDTO createAuthor() throws Exception {
        CreateUpdateAuthorDTO createUpdateAuthor = CreateUpdateAuthorDTO
            .builder()
            .name("Forrest Gump")
            .active(true)
            .archived(false)
            .info("Life is like a box of chocolates")
            .build();

        MvcResult result = post("/authors", createUpdateAuthor, status().isCreated());
        AuthorDTO authorDTO = resultToObject(result, AuthorDTO.class);

        // test if everything is on place
        Assertions.assertEquals(createUpdateAuthor.getName(), authorDTO.getName());
        Assertions.assertEquals(createUpdateAuthor.getInfo(), authorDTO.getInfo());
        Assertions.assertEquals(createUpdateAuthor.isActive(), authorDTO.isActive());
        Assertions.assertEquals(createUpdateAuthor.isArchived(), authorDTO.isArchived());
        Assertions.assertNotNull(authorDTO.getId());
        Assertions.assertNotNull(authorDTO.getCreatedDate());
        Assertions.assertNotNull(authorDTO.getLastUpdated());
        return authorDTO;
    }

    protected CategoryDTO createCategory() throws Exception {
        CreateUpdateCategoryDTO createUpdateCategory = CreateUpdateCategoryDTO
            .builder()
            .name("Life in Savannah")
            .build();

        MvcResult result = post("/categories", createUpdateCategory, status().isCreated());
        CategoryDTO categoryDTO = resultToObject(result, CategoryDTO.class);

        Assertions.assertNotNull(categoryDTO);
        Assertions.assertNotNull(categoryDTO.getId());
        Assertions.assertNotNull(categoryDTO.getCreatedDate());
        Assertions.assertNotNull(categoryDTO.getLastUpdated());
        Assertions.assertEquals(createUpdateCategory.getName(), categoryDTO.getName());

        return categoryDTO;
    }

    protected ThreadDTO createThread() throws Exception {
        AuthorDTO authorDTO = createAuthor();
        CategoryDTO categoryDTO = createCategory();

        CreateThreadDTO createThreadDTO = CreateThreadDTO
            .builder()
            .topic("Where is jenny?")
            .active(true)
            .authorId(authorDTO.getId())
            .categoryId(categoryDTO.getId())
            .build();

        MvcResult result = post("/threads", createThreadDTO, status().isCreated());
        ThreadDTO threadDTO = resultToObject(result, ThreadDTO.class);

        Assertions.assertEquals(createThreadDTO.getTopic(), threadDTO.getTopic());
        Assertions.assertEquals(createThreadDTO.getAuthorId(), threadDTO.getAuthorId());
        Assertions.assertEquals(createThreadDTO.getCategoryId(), threadDTO.getCategoryId());
        Assertions.assertNotNull(threadDTO.getCreatedDate());
        Assertions.assertNotNull(threadDTO.getLastUpdated());
        Assertions.assertNotNull(threadDTO.getId());
        Assertions.assertNull(threadDTO.getLastAnswerTime());

        return threadDTO;
    }

    protected PostDTO createPost(String authorId, String threadId) throws Exception {
        CreatePostDTO createPostDTO = CreatePostDTO
            .builder()
            .authorId(authorId)
            .content("I think she is somewhere in georgia")
            .threadId(threadId)
            .build();
        MvcResult result = post("/posts", createPostDTO, status().isCreated());
        PostDTO postDTO = resultToObject(result, PostDTO.class);

        Assertions.assertEquals(authorId, postDTO.getAuthorId());
        Assertions.assertEquals(threadId, postDTO.getThreadId());
        Assertions.assertEquals(createPostDTO.getContent(), postDTO.getContent());
        Assertions.assertNotNull(postDTO.getId());
        Assertions.assertNotNull(postDTO.getCreatedDate());
        Assertions.assertNotNull(postDTO.getLastUpdated());

        return postDTO;
    }
}
