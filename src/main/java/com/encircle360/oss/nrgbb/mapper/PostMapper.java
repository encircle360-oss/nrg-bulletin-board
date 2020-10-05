package com.encircle360.oss.nrgbb.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.encircle360.oss.nrgbb.dto.post.CreatePostDTO;
import com.encircle360.oss.nrgbb.dto.post.PostDTO;
import com.encircle360.oss.nrgbb.dto.post.UpdatePostDTO;
import com.encircle360.oss.nrgbb.model.Post;
import com.encircle360.oss.nrgbb.util.StringUtils;

@Mapper
public interface PostMapper {

    public static final PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    public PostDTO toDto(Post post);

    public List<PostDTO> toDto(List<Post> posts);

    public Post createFromDto(CreatePostDTO createPostDTO);

    public void updateFromDto(UpdatePostDTO updatePostDTO, @MappingTarget Post post);

    @AfterMapping
    default void afterFromDto(@MappingTarget Post post) {
        post.setContent(StringUtils.basicHtml(post.getContent()));
    }
}
