package com.encircle360.oss.nrgbb.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.encircle360.oss.nrgbb.dto.thread.CreateThreadDTO;
import com.encircle360.oss.nrgbb.dto.thread.ThreadDTO;
import com.encircle360.oss.nrgbb.dto.thread.UpdateThreadDTO;
import com.encircle360.oss.nrgbb.model.Thread;
import com.encircle360.oss.nrgbb.util.StringUtils;

@Mapper
public interface ThreadMapper {

    public static final ThreadMapper INSTANCE = Mappers.getMapper(ThreadMapper.class);

    public ThreadDTO toDto(Thread thread);

    public List<ThreadDTO> toDtos(List<Thread> threads);

    public Thread createFromDto(CreateThreadDTO createThreadDTO);

    @Mapping(target = "authorId", ignore = true)
    public void updateFromDto(UpdateThreadDTO updateThreadDTO, @MappingTarget Thread thread);

    @AfterMapping
    default void afterFromDto(@MappingTarget Thread thread) {
        thread.setTopic(StringUtils.noHtml(thread.getTopic()));
    }
}
