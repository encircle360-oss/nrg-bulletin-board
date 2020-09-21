package com.encircle360.oss.nrgbb.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import com.encircle360.oss.nrgbb.dto.category.CategoryDTO;
import com.encircle360.oss.nrgbb.dto.category.CreateUpdateCategoryDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest extends AbstractIntegrationTest {

    @Test
    CategoryDTO testCreation() throws Exception {
        CreateUpdateCategoryDTO createUpdateCategory = CreateUpdateCategoryDTO
            .builder()
            .build();

        MvcResult result = post("/categories", createUpdateCategory, status().isCreated());
        CategoryDTO categoryDTO = resultToObject(result, CategoryDTO.class);
        return categoryDTO;
    }

}
