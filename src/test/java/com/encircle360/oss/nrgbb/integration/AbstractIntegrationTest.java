package com.encircle360.oss.nrgbb.integration;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected <T> MvcResult post(String url, T object, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(object)))
            .andExpect(status)
            .andReturn();
    }

    protected <T> MvcResult put(String url, T object, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(object)))
            .andExpect(status)
            .andReturn();
    }

    protected MvcResult emptyPost(String url, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(url))
            .andExpect(status)
            .andReturn();
    }

    protected MvcResult get(String url, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(url))
            .andExpect(status)
            .andReturn();
    }

    protected MvcResult delete(String url, ResultMatcher status) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(url))
            .andExpect(status)
            .andReturn();
    }

    protected <T> T resultToObject(MvcResult result, Class<T> clazz) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }
}
