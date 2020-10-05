package com.encircle360.oss.nrgbb.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "threads")
@EqualsAndHashCode(callSuper = true)
public class Thread extends AbstractMongoDbEntity {

    private String topic;

    private String authorId;

    private String categoryId;

    private LocalDateTime lastAnswerTime;

    private boolean active;

    private boolean archived;
}
