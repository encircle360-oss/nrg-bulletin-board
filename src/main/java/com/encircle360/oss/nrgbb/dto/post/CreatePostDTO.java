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
    @Schema(name = "content", description = "Content of the post", example = "At the kiosk in my corner, you can buy the best energy drinks")
    private String content;

    @NotBlank
    @Schema(name = "authorId", description = "Id of the author from this post", example = "")
    private String authorId;

    @NotBlank
    @Schema(name = "threadId", description = "Id of the thread this post is related to", example = "")
    private String threadId;

    @Schema(name = "answerOf", description = "Id of the post this post is an answer to", example = "")
    private String answerOf;
}
