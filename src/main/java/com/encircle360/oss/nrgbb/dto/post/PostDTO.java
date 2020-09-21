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

    private String content;

    private String authorId;

    private String threadId;

    private String answerOf;
}
