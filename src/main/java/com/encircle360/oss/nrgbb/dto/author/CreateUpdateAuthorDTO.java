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
    @Schema(name = "name", description = "name of the author", example = "Forrest Gump")
    private String name;

    @NotBlank
    @Schema(name = "info", description = "some information about the author", example = "I'm living in georgia")
    private String info;

    @Schema(name = "active", description = "shows if the authors is active or not")
    private boolean active;

    @Schema(name = "archived", description = "shows if the authors is archived or not")
    private boolean archived;
}
