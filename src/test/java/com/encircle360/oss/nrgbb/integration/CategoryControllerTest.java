package com.encircle360.oss.nrgbb.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
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
    public void testCreation() throws Exception {
        CategoryDTO categoryDTO = create();
    }

    @Test
    public void testCreationAndDeletion() throws Exception {
        CategoryDTO categoryDTO = create();
        delete("/categories/" + categoryDTO.getId(), status().isNoContent());
    }

    @Test
    public void testCreationAndUpdate() throws Exception {
        CategoryDTO categoryDTO = create();
        CreateUpdateCategoryDTO createUpdateCategory = CreateUpdateCategoryDTO
            .builder()
            .name("Life in Fuschl am See")
            .build();

        MvcResult mvcResult = put("/categories/" + categoryDTO.getId(), createUpdateCategory, status().isOk());
        CategoryDTO updatedCategory = resultToObject(mvcResult, CategoryDTO.class);

        Assertions.assertNotNull(updatedCategory);
        Assertions.assertEquals("Life in Fuschl am See", updatedCategory.getName());
        Assertions.assertEquals(categoryDTO.getId(), updatedCategory.getId());
        Assertions.assertEquals(categoryDTO.getCreatedDate().truncatedTo(ChronoUnit.SECONDS), updatedCategory.getCreatedDate().truncatedTo(ChronoUnit.SECONDS));
    }

    public CategoryDTO create() throws Exception {
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

}
