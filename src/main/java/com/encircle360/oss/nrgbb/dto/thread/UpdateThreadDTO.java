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
@Schema(name = "UpdateThread", example = "updates a thread")
public class UpdateThreadDTO {

    @NotBlank
    private String topic;

    @NotNull
    private boolean active;

    @NotNull
    private String categoryId;

    @NotNull
    private boolean archived;

}
