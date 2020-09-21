package com.encircle360.oss.nrgbb.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.encircle360.oss.nrgbb.dto.author.AuthorDTO;
import com.encircle360.oss.nrgbb.dto.author.CreateUpdateAuthorDTO;
import com.encircle360.oss.nrgbb.model.Author;
import com.encircle360.oss.nrgbb.util.StringUtils;

@Mapper
public interface AuthorMapper {

    public static final AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    public AuthorDTO toDTO(Author author);

    public List<AuthorDTO> toDTOs(List<Author> authors);

    public Author createFromDto(CreateUpdateAuthorDTO createUpdateAuthor);

    public void updateFromDto(CreateUpdateAuthorDTO createUpdateAuthor, @MappingTarget Author author);

    @AfterMapping
    default void afterFromDto(@MappingTarget Author author) {
        author.setName(StringUtils.noHtml(author.getName()));
        author.setInfo(StringUtils.basicHtml(author.getInfo()));
    }
}
