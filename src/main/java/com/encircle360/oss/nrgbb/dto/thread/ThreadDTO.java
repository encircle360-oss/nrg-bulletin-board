package com.encircle360.oss.nrgbb.dto.thread;

import java.time.LocalDateTime;

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
@Schema(name = "Thread", description = "returns a thread with its topic, authorId and co.")
public class ThreadDTO extends EntityDTO {

    @Schema(name = "topic", description = "Topic of a thread", example = "Where to buy best Energy Drinks?")
    private String topic;

    @Schema(name = "authorId", description = "Id of the author, who has written the thread", example = "5f68b8ae4fb93878ff6bd92f")
    private String authorId;

    @Schema(name = "categoryId", description = "Id of the category this thread should have", example = "5f68b8ae4fb93878ff6bd92f")
    private String categoryId;

    @Schema(name = "lastAnswerTime", description = "Last time someone answered to this thread", example = "")
    private LocalDateTime lastAnswerTime;

    @Schema(name = "active", description = "determines if a thread is active or not", example = "true")
    private boolean active;

    @Schema(name = "archived", description = "determines if a thread is archived or not", example = "false")
    private boolean archived;
}
