package com.encircle360.oss.nrgbb.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encircle360.oss.nrgbb.dto.author.AuthorDTO;
import com.encircle360.oss.nrgbb.dto.author.CreateUpdateAuthorDTO;
import com.encircle360.oss.nrgbb.dto.pagination.PageContainer;
import com.encircle360.oss.nrgbb.dto.pagination.PageContainerFactory;
import com.encircle360.oss.nrgbb.mapper.AuthorMapper;
import com.encircle360.oss.nrgbb.model.Author;
import com.encircle360.oss.nrgbb.service.AuthorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private final AuthorService authorService;

    private final PageContainerFactory<AuthorDTO> pageContainerFactory = new PageContainerFactory<>();

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageContainer<AuthorDTO>> getAll(@RequestParam(required = false) final Integer size,
                                                           @RequestParam(required = false) final Integer page,
                                                           @RequestParam(required = false) final String sort) {
        Pageable pageable = pageContainerFactory.mapRequestToPageable(size, page, sort);
        Page<Author> authorPage = authorService.getAll(pageable);
        List<AuthorDTO> authorDTOs = authorMapper.toDTOs(authorPage.getContent());

        PageContainer<AuthorDTO> pageContainer = pageContainerFactory.getPageContainer(pageable, authorPage, authorDTOs);

        return ResponseEntity.status(HttpStatus.OK).body(pageContainer);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> get(@PathVariable final String id) {
        Author author = authorService.get(id);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        AuthorDTO authorDTO = authorMapper.toDTO(author);
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> create(@RequestBody @Valid final CreateUpdateAuthorDTO createUpdateAuthor) {
        Author author = authorMapper.createFromDto(createUpdateAuthor);
        author = authorService.save(author);

        AuthorDTO authorDTO = authorMapper.toDTO(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorDTO);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDTO> update(@PathVariable final String id, @RequestBody @Valid final CreateUpdateAuthorDTO createUpdateAuthor) {
        Author author = authorService.get(id);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        authorMapper.updateFromDto(createUpdateAuthor, author);
        author = authorService.save(author);

        AuthorDTO authorDTO = authorMapper.toDTO(author);
        return ResponseEntity.status(HttpStatus.OK).body(authorDTO);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        Author author = authorService.get(id);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        authorService.delete(author);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
