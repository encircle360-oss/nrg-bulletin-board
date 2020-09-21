package com.encircle360.oss.nrgbb.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import com.encircle360.oss.nrgbb.dto.author.AuthorDTO;
import com.encircle360.oss.nrgbb.dto.author.CreateUpdateAuthor;
import com.encircle360.oss.nrgbb.dto.category.CategoryDTO;
import com.encircle360.oss.nrgbb.dto.category.CreateUpdateCategoryDTO;
import com.encircle360.oss.nrgbb.dto.pagination.PageContainer;
import com.encircle360.oss.nrgbb.dto.post.CreatePostDTO;
import com.encircle360.oss.nrgbb.dto.post.PostDTO;
import com.encircle360.oss.nrgbb.dto.thread.CreateThreadDTO;
import com.encircle360.oss.nrgbb.dto.thread.ThreadDTO;
import com.encircle360.oss.nrgbb.dto.thread.UpdateThreadDTO;
import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootTest
@AutoConfigureMockMvc
public class ThreadControllerTest extends AbstractIntegrationTest {

    AuthorDTO createAuthor() throws Exception {
        CreateUpdateAuthor createUpdateAuthor = CreateUpdateAuthor
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

    public CategoryDTO createCategory() throws Exception {
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

    ThreadDTO create() throws Exception {
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

    @Test
    void testCreation() throws Exception {
        create();
    }

    @Test
    void testUpdate() throws Exception {
        ThreadDTO threadDTO = create();
        UpdateThreadDTO updateThreadDTO = UpdateThreadDTO
            .builder()
            .topic("Where is dietrich?")
            .categoryId(threadDTO.getCategoryId())
            .active(true)
            .archived(false)
            .build();

        MvcResult result = put("/threads/" + threadDTO.getId(), updateThreadDTO, status().isOk());
        ThreadDTO updatedThread = resultToObject(result, ThreadDTO.class);

        Assertions.assertEquals(updateThreadDTO.getTopic(), updatedThread.getTopic());
        Assertions.assertEquals(updateThreadDTO.getCategoryId(), updatedThread.getCategoryId());
        Assertions.assertNotNull(updatedThread.getCreatedDate());
        Assertions.assertNotNull(updatedThread.getLastUpdated());
        Assertions.assertNotNull(updatedThread.getId());
        Assertions.assertNull(updatedThread.getLastAnswerTime());
    }

    @Test
    void testDelete() throws Exception {
        ThreadDTO threadDTO = create();
        CreatePostDTO createPostDTO = CreatePostDTO
            .builder()
            .authorId(threadDTO.getAuthorId())
            .content("I think she is somewhere in georgia")
            .threadId(threadDTO.getId())
            .build();
        post("/posts", createPostDTO, status().isCreated());
        delete("/threads/" + threadDTO.getId(), status().isNoContent());
        MvcResult result = get("/posts?threadId=" + threadDTO.getId(), status().isOk());
        PageContainer<PostDTO> postDTOPageContainer = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(0, postDTOPageContainer.getPagination().getTotalElements());
    }

    @Test
    void testBadRequest() throws Exception {
        ThreadDTO threadDTO = create();
        CreateThreadDTO createThreadDTO = CreateThreadDTO.builder().build();
        post("/threads", createThreadDTO, status().isBadRequest());
        put("/threads/" + threadDTO.getId(), createThreadDTO, status().isBadRequest());
    }
}
