package com.encircle360.oss.nrgbb.dto.category;

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
@Schema(name = "CreateUpdateCategory", description = "Creates a category with the given name")
public class CreateUpdateCategoryDTO {

    @NotBlank
    @Schema(name = "name", description = "a name for the category", example = "All about Energy Drinks")
    private String name;
}
