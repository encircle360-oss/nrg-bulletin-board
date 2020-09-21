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
@Schema(name = "UpdatePost", description = "Updates a post's content")
public class UpdatePostDTO {

    @NotBlank
    @Size(min = 20)
    private String content;

}
