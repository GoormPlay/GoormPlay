package com.goormplay.contentservice.tool;

import com.goormplay.contentservice.dto.ContentDTO;
import com.goormplay.contentservice.entity.Content;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    @Mapping(target = "id", ignore = true)  // id 필드 무시
    @Mapping(target = "likesCount", constant = "0")  // 기본값 설정
    @Mapping(target = "commentsCount", constant = "0")
    @Mapping(target = "createdAt", ignore = true)  // 자동 생성 필드 무시
    @Mapping(target = "updatedAt", ignore = true)
    Content toEntity(ContentDTO contentDTO);
    ContentDTO toDTO(Content content);
}
