package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.model.CommentModel;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.dto.update.CommentUpdateDto;
import com.mjc.school.service.mapper.helper.CommentMapperHelper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
        , uses = {NewsMapper.class, CommentMapperHelper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {

    @Mapping(source = "newsId", target = "news")
    CommentModel dtoToModel(CommentDto dto);

    @Mapping(source = "news", target = "newsId")
    CommentDto modelToDto(CommentModel model);

    List<CommentDto> modelToDtoList(List<CommentModel> model);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(source = "newsId", target = "news")
    CommentModel toComment(CommentUpdateDto commentUpdateDto);
}
