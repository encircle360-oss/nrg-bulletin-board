package com.encircle360.oss.nrgbb.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import com.encircle360.oss.nrgbb.dto.pagination.PageContainer;
import com.encircle360.oss.nrgbb.dto.post.PostDTO;
import com.encircle360.oss.nrgbb.dto.thread.CreateThreadDTO;
import com.encircle360.oss.nrgbb.dto.thread.ThreadDTO;
import com.encircle360.oss.nrgbb.dto.thread.UpdateThreadDTO;
import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootTest
@AutoConfigureMockMvc
public class ThreadControllerTest extends AbstractIntegrationTest {

    @Test
    void testCreation() throws Exception {
        createThread();
    }

    @Test
    void testUpdate() throws Exception {
        ThreadDTO threadDTO = createThread();
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
        ThreadDTO threadDTO = createThread();
        PostDTO postDTO = createPost(threadDTO.getAuthorId(), threadDTO.getId());

        delete("/threads/" + threadDTO.getId(), status().isNoContent());
        MvcResult result = get("/posts?threadId=" + threadDTO.getId(), status().isOk());
        PageContainer<PostDTO> postDTOPageContainer = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertEquals(0, postDTOPageContainer.getPagination().getTotalElements());
    }

    @Test
    void testBadRequest() throws Exception {
        ThreadDTO threadDTO = createThread();
        CreateThreadDTO createThreadDTO = CreateThreadDTO.builder().build();
        post("/threads", createThreadDTO, status().isBadRequest());
        put("/threads/" + threadDTO.getId(), createThreadDTO, status().isBadRequest());
    }
}
