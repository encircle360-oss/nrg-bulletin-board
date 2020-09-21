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
    @Schema(name = "topic", description = "Topic of a thread", example = "Where to buy best Energy Drinks?")
    private String topic;

    @NotNull
    @Schema(name = "categoryId", description = "Id of the category this thread should have", example = "5f68b8ae4fb93878ff6bd92f")
    private String categoryId;

    @NotNull
    @Schema(name = "active", description = "determines if a thread is active or not", example = "true")
    private boolean active;

    @NotNull
    @Schema(name = "archived", description = "determines if a thread is archived or not", example = "false")
    private boolean archived;

}
