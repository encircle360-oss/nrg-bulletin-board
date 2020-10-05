package com.encircle360.oss.nrgbb.dto.category;

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
@Schema(name = "Category", description = "Category for threads")
public class CategoryDTO extends EntityDTO {

    @Schema(name = "name", description = "a name for the category", example = "All about Energy Drinks")
    private String name;
}
