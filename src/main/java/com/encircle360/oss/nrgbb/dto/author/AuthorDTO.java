package com.encircle360.oss.nrgbb.dto.author;

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
@Schema(name = "Author", description = "Author of a thread or post")
public class AuthorDTO extends EntityDTO {

    @Schema(name = "name", description = "name of the author", example = "Forrest Gump")
    private String name;

    @Schema(name = "email", description = "email of the author", example = "forrest.gump@localhost")
    private String email;

    @Schema(name = "info", description = "some information about the author", example = "I'm living in georgia")
    private String info;

    @Schema(name = "active", description = "shows if the authors is active or not", example = "true")
    private boolean active;

    @Schema(name = "archived", description = "shows if the authors is archived or not", example = "false")
    private boolean archived;
}
