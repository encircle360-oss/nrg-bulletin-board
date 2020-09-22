package com.encircle360.oss.nrgbb.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
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
import com.encircle360.oss.nrgbb.security.Roles;
import com.encircle360.oss.nrgbb.service.PostService;
import com.encircle360.oss.nrgbb.service.ThreadService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/threads")
public class ThreadController {

    private final ThreadService threadService;
    private final PostService postService;

    private final ThreadMapper threadMapper = ThreadMapper.INSTANCE;

    private final PageContainerFactory<ThreadDTO> pageContainerFactory = new PageContainerFactory<>();

    @Secured(Roles.Thread.CAN_LIST)
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @Operation(operationId = "getAllThreads", description = "returns all threads in a pageable way, you can filter with authorId and/or categoryId")
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

    @Secured(Roles.Thread.CAN_GET)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(operationId = "getThread", description = "returns one thread by id")
    public ResponseEntity<ThreadDTO> get(@PathVariable final String id) {
        Thread thread = threadService.get(id);
        if (thread == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ThreadDTO threadDto = threadMapper.toDto(thread);
        return ResponseEntity.status(HttpStatus.OK).body(threadDto);
    }

    @Secured(Roles.Thread.CAN_CREATE)
    @Operation(operationId = "createThread", description = "Creates a thread")
    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadDTO> create(@RequestBody @Valid final CreateThreadDTO createThreadDTO) {
        Thread thread = threadMapper.createFromDto(createThreadDTO);
        thread = threadService.save(thread);
        ThreadDTO threadDTO = threadMapper.toDto(thread);
        return ResponseEntity.status(HttpStatus.CREATED).body(threadDTO);
    }

    @Secured({Roles.Thread.CAN_UPDATE, Roles.Thread.CAN_UPDATE_OWN})
    @Operation(operationId = "updateThread", description = "updates a thread by id")
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ThreadDTO> update(@PathVariable final String id, @RequestBody @Valid final UpdateThreadDTO updateThreadDTO) {
        Thread thread = threadService.get(id);
        if (thread == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        threadMapper.updateFromDto(updateThreadDTO, thread);
        ThreadDTO threadDTO = threadMapper.toDto(thread);
        return ResponseEntity.status(HttpStatus.OK).body(threadDTO);
    }

    @Secured({Roles.Thread.CAN_DELETE, Roles.Thread.CAN_DELETE_OWN})
    @Operation(operationId = "deleteThread", description = "deletes a thread by id")
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        Thread thread = threadService.get(id);
        if (thread == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        postService.deleteByThreadId(thread.getId());
        threadService.delete(thread);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
