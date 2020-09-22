package com.encircle360.oss.nrgbb.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.LocalDateTime;
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
import com.encircle360.oss.nrgbb.dto.post.CreatePostDTO;
import com.encircle360.oss.nrgbb.dto.post.PostDTO;
import com.encircle360.oss.nrgbb.dto.post.UpdatePostDTO;
import com.encircle360.oss.nrgbb.mapper.PostMapper;
import com.encircle360.oss.nrgbb.model.Author;
import com.encircle360.oss.nrgbb.model.Post;
import com.encircle360.oss.nrgbb.model.Thread;
import com.encircle360.oss.nrgbb.security.Roles;
import com.encircle360.oss.nrgbb.service.AuthorService;
import com.encircle360.oss.nrgbb.service.PostService;
import com.encircle360.oss.nrgbb.service.ThreadService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final AuthorService authorService;
    private final ThreadService threadService;

    private final PostMapper postMapper = PostMapper.INSTANCE;

    private final PageContainerFactory<PostDTO> pageContainerFactory = new PageContainerFactory<>();

    @Secured(Roles.Post.CAN_LIST)
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @Operation(operationId = "getAllPosts", description = "returns all posts in a pageable way, you can also filter with threadId and/or authorId")
    public ResponseEntity<PageContainer<PostDTO>> getAll(@RequestParam(required = false) final Integer size,
                                                         @RequestParam(required = false) final Integer page,
                                                         @RequestParam(required = false) final String sort,
                                                         @RequestParam(required = false) final String threadId,
                                                         @RequestParam(required = false) final String authorId) {
        Pageable pageable = pageContainerFactory.mapRequestToPageable(size, page, sort);
        Page<Post> postPage = postService.getAll(threadId, authorId, pageable);
        List<PostDTO> postDtos = postMapper.toDto(postPage.getContent());

        PageContainer<PostDTO> pageContainer = pageContainerFactory.getPageContainer(pageable, postPage, postDtos);
        return ResponseEntity.status(HttpStatus.OK).body(pageContainer);
    }

    @Secured(Roles.Post.CAN_GET)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(operationId = "getPost", description = "returns a post by its id")
    public ResponseEntity<PostDTO> get(@PathVariable final String id) {
        Post post = postService.get(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        PostDTO postDto = postMapper.toDto(post);

        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }

    @Secured({Roles.Post.CAN_CREATE, Roles.Post.CAN_CREATE_OWN})
    @Operation(operationId = "createPost", description = "creates a post")
    @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTO> create(@RequestBody @Valid CreatePostDTO createPostDTO) {
        Author author = authorService.get(createPostDTO.getAuthorId());
        Thread thread = threadService.get(createPostDTO.getThreadId());
        if (author == null || thread == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Post post = postMapper.createFromDto(createPostDTO);
        post = postService.save(post);
        PostDTO postDTO = postMapper.toDto(post);

        // sets last answertime
        thread.setLastAnswerTime(LocalDateTime.now());
        threadService.save(thread);

        return ResponseEntity.status(HttpStatus.CREATED).body(postDTO);
    }

    @Secured({Roles.Post.CAN_UPDATE, Roles.Post.CAN_UPDATE_OWN})
    @Operation(operationId = "updatePost", description = "updates a post by its id")
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTO> update(@PathVariable final String id, @RequestBody @Valid UpdatePostDTO updatePostDTO) {
        Post post = postService.get(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        postMapper.updateFromDto(updatePostDTO, post);
        post = postService.save(post);

        PostDTO postDTO = postMapper.toDto(post);

        return ResponseEntity.status(HttpStatus.OK).body(postDTO);
    }

    @Secured({Roles.Post.CAN_DELETE, Roles.Post.CAN_DELETE_OWN})
    @Operation(operationId = "deletePost", description = "deletes a post by its id")
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        Post post = postService.get(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        postService.delete(post);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
