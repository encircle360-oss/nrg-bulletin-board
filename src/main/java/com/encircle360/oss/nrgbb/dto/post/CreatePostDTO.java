package com.encircle360.oss.nrgbb.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreatePost", description = "creates a post in the given thread, by the given author")
public class CreatePostDTO {

    @NotBlank
    @Size(min = 20)
    private String content;

    @NotBlank
    private String authorId;

    @NotBlank
    private String threadId;

    private String parentOf;
}
