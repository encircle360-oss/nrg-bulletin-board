package com.encircle360.oss.nrgbb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ThreadMapper {

    public static final ThreadMapper INSTANCE = Mappers.getMapper(ThreadMapper.class);

}
