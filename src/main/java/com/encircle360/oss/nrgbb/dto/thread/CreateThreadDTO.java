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
    @Schema(name = "topic", description = "Topic of a thread", example = "Where to buy best Energy Drinks?")
    private String topic;

    @NotBlank
    @Schema(name = "authorId", description = "Id of the author, who has written the thread", example = "5f68b8ae4fb93878ff6bd92f")
    private String authorId;

    @NotBlank
    @Schema(name = "categoryId", description = "Id of the category this thread should have", example = "5f68b8ae4fb93878ff6bd92f")
    private String categoryId;

    @NotNull
    @Schema(name = "active", description = "determines if a thread is active or not", example = "true")
    private boolean active;

}
