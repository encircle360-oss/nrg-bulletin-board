package com.encircle360.oss.nrgbb.dto.author;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreateUpdateAuthor", description = "Creates an author in database")
public class CreateUpdateAuthorDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String info;

    private boolean active;

    private boolean archived;
}
