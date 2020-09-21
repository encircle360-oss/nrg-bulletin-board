package com.encircle360.oss.nrgbb.dto.thread;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateThreadDTO {

    @NotBlank
    private String topic;

    @NotBlank
    private String authorId;

    @NotBlank
    private String categoryId;

    @NotNull
    private boolean active;

}
