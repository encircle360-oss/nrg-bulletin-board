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

    private String name;

    private String info;

    private boolean active;

    private boolean archived;
}
