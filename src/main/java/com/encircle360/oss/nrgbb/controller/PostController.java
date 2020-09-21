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
import com.encircle360.oss.nrgbb.dto.post.CreatePostDTO;
import com.encircle360.oss.nrgbb.dto.post.PostDTO;
import com.encircle360.oss.nrgbb.dto.post.UpdatePostDTO;
import com.encircle360.oss.nrgbb.mapper.PostMapper;
import com.encircle360.oss.nrgbb.model.Author;
import com.encircle360.oss.nrgbb.model.Post;
import com.encircle360.oss.nrgbb.model.Thread;
import com.encircle360.oss.nrgbb.service.AuthorService;
import com.encircle360.oss.nrgbb.service.PostService;
import com.encircle360.oss.nrgbb.service.ThreadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final AuthorService authorService;
    private final ThreadService threadService;

    private final PostMapper postMapper = PostMapper.INSTANCE;

    private final PageContainerFactory<PostDTO> pageContainerFactory = new PageContainerFactory<>();

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTO> get(@PathVariable final String id) {
        Post post = postService.get(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        PostDTO postDto = postMapper.toDto(post);

        return ResponseEntity.status(HttpStatus.OK).body(postDto);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTO> create(@RequestBody @Valid CreatePostDTO createPostDTO) {
        Author author = authorService.get(createPostDTO.getAuthorId());
        Thread thread = threadService.get(createPostDTO.getThreadId());
        if (author == null || thread == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Post post = postMapper.createFromDto(createPostDTO);
        post = postService.save(post);
        PostDTO postDTO = postMapper.toDto(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        Post post = postService.get(id);
        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        postService.delete(post);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
