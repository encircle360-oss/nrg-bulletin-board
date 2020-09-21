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

import com.encircle360.oss.nrgbb.dto.pagination.PageContainer;
import com.encircle360.oss.nrgbb.dto.pagination.PageContainerFactory;
import com.encircle360.oss.nrgbb.dto.thread.CreateThreadDTO;
import com.encircle360.oss.nrgbb.dto.thread.ThreadDTO;
import com.encircle360.oss.nrgbb.dto.thread.UpdateThreadDTO;
import com.encircle360.oss.nrgbb.mapper.ThreadMapper;
import com.encircle360.oss.nrgbb.model.Thread;
import com.encircle360.oss.nrgbb.service.ThreadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/threads")
@RequiredArgsConstructor
public class ThreadController {

    private final ThreadMapper threadMapper = ThreadMapper.INSTANCE;

    private final ThreadService threadService;

    private final PageContainerFactory<ThreadDTO> pageContainerFactory = new PageContainerFactory<>();

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageContainer<ThreadDTO>> getAll(@RequestParam(required = false) final Integer size,
                                                           @RequestParam(required = false) final Integer page,
                                                           @RequestParam(required = false) final String sort,
                                                           @RequestParam(required = false) final String authorId,
                                                           @RequestParam(required = false) final String categoryId) {
        Pageable pageable = pageContainerFactory.mapRequestToPageable(size, page, sort);
        Page<Thread> threadPage = threadService.getAll(authorId, categoryId, pageable);
        List<ThreadDTO> threadDTOs = threadMapper.toDtos(threadPage.getContent());

        PageContainer<ThreadDTO> pageContainer = pageContainerFactory.getPageContainer(pageable, threadPage, threadDTOs);

        return ResponseEntity.status(HttpStatus.OK).body(pageContainer);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadDTO> get(@PathVariable final String id) {
        Thread thread = threadService.get(id);
        if (thread == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ThreadDTO threadDto = threadMapper.toDto(thread);
        return ResponseEntity.status(HttpStatus.OK).body(threadDto);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadDTO> create(@RequestBody @Valid final CreateThreadDTO createThreadDTO) {
        Thread thread = threadMapper.createFromDto(createThreadDTO);
        thread = threadService.save(thread);
        ThreadDTO threadDTO = threadMapper.toDto(thread);
        return ResponseEntity.status(HttpStatus.CREATED).body(threadDTO);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadDTO> update(@PathVariable final String id, @RequestBody @Valid final UpdateThreadDTO updateThreadDTO) {
        Thread thread = threadService.get(id);
        if (thread == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        threadMapper.updateFromDto(updateThreadDTO, thread);
        ThreadDTO threadDTO = threadMapper.toDto(thread);
        return ResponseEntity.status(HttpStatus.OK).body(threadDTO);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        Thread thread = threadService.get(id);
        if (thread == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        threadService.delete(thread);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
