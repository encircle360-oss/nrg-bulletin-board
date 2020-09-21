package com.encircle360.oss.nrgbb.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
