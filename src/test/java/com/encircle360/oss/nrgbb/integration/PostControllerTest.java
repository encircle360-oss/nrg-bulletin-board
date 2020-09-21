package com.encircle360.oss.nrgbb.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import com.encircle360.oss.nrgbb.dto.post.PostDTO;
import com.encircle360.oss.nrgbb.dto.post.UpdatePostDTO;
import com.encircle360.oss.nrgbb.dto.thread.ThreadDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest extends AbstractIntegrationTest {

    @Test
    void testCreation() throws Exception {
        ThreadDTO threadDTO = createThread();
        createPost(threadDTO.getAuthorId(), threadDTO.getId());
    }

    @Test
    void testUpdate() throws Exception {
        ThreadDTO threadDTO = createThread();
        PostDTO postDTO = createPost(threadDTO.getAuthorId(), threadDTO.getId());
        UpdatePostDTO updatePostDTO = UpdatePostDTO
            .builder()
            .content("Or somewhere in austria")
            .build();

        MvcResult result = put("/posts/" + postDTO.getId(), updatePostDTO, status().isOk());
        PostDTO updatedPost = resultToObject(result, PostDTO.class);

        Assertions.assertEquals(postDTO.getId(), updatedPost.getId());
        Assertions.assertEquals(postDTO.getAuthorId(), updatedPost.getAuthorId());
        Assertions.assertEquals(postDTO.getThreadId(), updatedPost.getThreadId());
        Assertions.assertEquals(updatePostDTO.getContent(), updatedPost.getContent());
    }

    @Test
    void testDelete() throws Exception {
        ThreadDTO threadDTO = createThread();
        PostDTO postDTO = createPost(threadDTO.getAuthorId(), threadDTO.getId());
        delete("/posts/" + postDTO.getId(), status().isNoContent());
    }

    @Test
    void testGet() throws Exception {
        ThreadDTO threadDTO = createThread();
        PostDTO postDTO = createPost(threadDTO.getAuthorId(), threadDTO.getId());
        MvcResult result = get("/posts/" + postDTO.getId(), status().isOk());
        PostDTO getPost = resultToObject(result, PostDTO.class);

        Assertions.assertEquals(postDTO.getId(), getPost.getId());
        Assertions.assertEquals(postDTO.getAuthorId(), getPost.getAuthorId());
        Assertions.assertEquals(postDTO.getThreadId(), getPost.getThreadId());
        Assertions.assertEquals(postDTO.getAnswerOf(), getPost.getAnswerOf());
        Assertions.assertEquals(postDTO.getContent(), getPost.getContent());
        Assertions.assertEquals(postDTO.getLastUpdated().truncatedTo(ChronoUnit.MINUTES), getPost.getLastUpdated().truncatedTo(ChronoUnit.MINUTES));
        Assertions.assertEquals(postDTO.getCreatedDate().truncatedTo(ChronoUnit.MINUTES), getPost.getCreatedDate().truncatedTo(ChronoUnit.MINUTES));
    }
}
