package com.encircle360.oss.nrgbb.dto.author;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateAuthor {

    private String name;

    private String info;

    private boolean active;

    private boolean archived;
}
