package com.encircle360.oss.nrgbb.model;

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
@Document(collection = "authors")
@EqualsAndHashCode(callSuper = true)
public class Author extends AbstractMongoDbEntity {

    private String name;

    private String info;

    private boolean active;

    private boolean archived;
}
