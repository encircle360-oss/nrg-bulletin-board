package com.encircle360.oss.nrgbb.dto.category;

import com.encircle360.oss.nrgbb.dto.EntityDTO;

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
public class CategoryDTO extends EntityDTO {

    private String name;
}
