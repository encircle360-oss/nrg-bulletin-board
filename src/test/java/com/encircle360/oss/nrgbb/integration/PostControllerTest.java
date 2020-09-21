package com.encircle360.oss.nrgbb.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
