package com.encircle360.oss.nrgbb.dto.thread;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreateThread", description = "Creates a thread with the given topic, content will be served with posts")
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
