package com.encircle360.oss.nrgbb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    public static final AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

}
