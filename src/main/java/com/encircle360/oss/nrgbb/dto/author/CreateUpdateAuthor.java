package com.encircle360.oss.nrgbb.dto.author;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateAuthor {

    @NotBlank
    private String name;

    @NotBlank
    private String info;

    private boolean active;

    private boolean archived;
}
