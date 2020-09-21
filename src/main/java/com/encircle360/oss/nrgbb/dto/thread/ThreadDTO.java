package com.encircle360.oss.nrgbb.dto.thread;

import java.time.LocalDateTime;

import com.encircle360.oss.nrgbb.dto.EntityDTO;

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
public class ThreadDTO extends EntityDTO {

    private String topic;

    private String authorId;

    private String categoryId;

    private LocalDateTime lastAnswerTime;

    private boolean active;

    private boolean archived;
}
