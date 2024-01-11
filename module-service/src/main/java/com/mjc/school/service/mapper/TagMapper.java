package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.model.TagModel;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.dto.update.TagUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagMapper {
    @Mapping(target = "news",ignore = true)
    TagModel dtoToModel(TagDto dto);

    TagDto modelToDto(TagModel model);

    @Mapping(target = "news",ignore = true)
    List<TagDto> modelToDtoList(List<TagModel> model);

    @Mapping(target = "news",ignore = true)
    TagModel toTag(TagUpdateDto newsUpdateDtoDto);
}
