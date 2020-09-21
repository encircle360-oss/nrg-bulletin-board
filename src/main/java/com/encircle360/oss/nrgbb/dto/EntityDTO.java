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

    private String id;

    private LocalDateTime createdDate;

    private LocalDateTime lastUpdated;
}
