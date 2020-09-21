package com.encircle360.oss.nrgbb.dto.post;

import com.encircle360.oss.nrgbb.dto.EntityDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(name = "Post", description = "Contains all information of a post")
public class PostDTO extends EntityDTO {
    @Schema(name = "content", description = "Content of the post", example = "At the kiosk in my corner, you can buy the best energy drinks")
    private String content;

    @Schema(name = "authorId", description = "Id of the author from this post", example = "")
    private String authorId;

    @Schema(name = "threadId", description = "Id of the thread this post is related to", example = "")
    private String threadId;

    @Schema(name = "answerOf", description = "Id of the post this post is an answer to", example = "")
    private String answerOf;
}
