package com.encircle360.oss.nrgbb.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;

import com.encircle360.oss.nrgbb.dto.author.AuthorDTO;
import com.encircle360.oss.nrgbb.dto.author.CreateUpdateAuthor;
import com.encircle360.oss.nrgbb.dto.pagination.PageContainer;
import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest extends AbstractIntegrationTest {

    @Test
    AuthorDTO testCreation() throws Exception {
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

    @Test
    void testCreationAndDelete() throws Exception {
        AuthorDTO authorDTO = testCreation();
        delete("/authors/" + authorDTO.getId(), status().isNoContent());
    }

    @Test
    void testCreationAndGet() throws Exception {
        AuthorDTO authorDTO = testCreation();
        MvcResult result = get("/authors/" + authorDTO.getId(), status().isOk());
        AuthorDTO getAuthor = resultToObject(result, AuthorDTO.class);

        assertAuthors(authorDTO, getAuthor);

        result = get("/authors", status().isOk());
        PageContainer<AuthorDTO> all = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        Assertions.assertNotNull(all);
        Assertions.assertEquals(1, all.getPagination().getTotalElements());

        assertAuthors(authorDTO, all.getContent().get(0));
    }

    void assertAuthors(AuthorDTO authorDTO, AuthorDTO getAuthor) {
        Assertions.assertEquals(authorDTO.getName(), getAuthor.getName());
        Assertions.assertEquals(authorDTO.getInfo(), getAuthor.getInfo());
        Assertions.assertEquals(authorDTO.isActive(), getAuthor.isActive());
        Assertions.assertEquals(authorDTO.isArchived(), getAuthor.isArchived());
        Assertions.assertEquals(authorDTO.getId(), getAuthor.getId());
        Assertions.assertEquals(authorDTO.getCreatedDate().truncatedTo(ChronoUnit.SECONDS), getAuthor.getCreatedDate().truncatedTo(ChronoUnit.SECONDS));
        Assertions.assertEquals(authorDTO.getLastUpdated().truncatedTo(ChronoUnit.SECONDS), getAuthor.getLastUpdated().truncatedTo(ChronoUnit.SECONDS));
    }

}
