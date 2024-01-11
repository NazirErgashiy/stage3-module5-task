package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.model.NewsModel;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.update.NewsUpdateDto;
import com.mjc.school.service.mapper.helper.NewsMapperHelper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CommentMapper.class, AuthorMapper.class, TagMapper.class, NewsMapperHelper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface NewsMapper {

    @Mapping(source = "authorId", target = "author")
    @Mapping(source = "tagsId", target = "tags")
    @Mapping(source = "commentsId", target = "comments")
    NewsModel dtoToModel(NewsDto dto);

    @Mapping(source = "author", target = "authorId")
    @Mapping(source = "tags", target = "tagsId")
    @Mapping(source = "comments", target = "commentsId")
    NewsDto modelToDto(NewsModel model);

    List<NewsDto> modelToDtoList(List<NewsModel> model);

    @Mapping(target = "createDate",ignore = true)
    @Mapping(target = "lastUpdatedDate",ignore = true)
    @Mapping(target = "author",ignore = true)
    @Mapping(target = "comments",ignore = true)
    @Mapping(source = "tagsId", target = "tags")
    NewsModel toNews(NewsUpdateDto newsUpdateDto);
}
