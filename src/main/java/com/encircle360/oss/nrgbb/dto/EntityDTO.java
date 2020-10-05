package com.encircle360.oss.nrgbb.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Entity")
public abstract class EntityDTO {

    @Schema(name = "id", description = "Identifier of the entity", example = "5f68b8ae4fb93878ff6bd92f")
    private String id;

    @Schema(name = "createdDate", description = "Date the entity was created", example = "")
    private LocalDateTime createdDate;

    @Schema(name = "lastUpdated", description = "Date the entity was updated last", example = "")
    private LocalDateTime lastUpdated;
}
