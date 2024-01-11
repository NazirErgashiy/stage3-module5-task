package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.update.AuthorUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorMapper {

    @Mapping(target = "newsId",ignore = true)
    AuthorModel dtoToModel(AuthorDto dto);

    AuthorDto modelToDto(AuthorModel model);

    @Mapping(target = "createDate",ignore = true)
    @Mapping(target = "lastUpdatedDate",ignore = true)
    @Mapping(target = "newsId",ignore = true)
    List<AuthorDto> modelToDtoList(List<AuthorModel> model);

    @Mapping(target = "createDate",ignore = true)
    @Mapping(target = "lastUpdatedDate",ignore = true)
    @Mapping(target = "newsId",ignore = true)
    AuthorModel toAuthor(AuthorUpdateDto articleUpdateDto);

    /*
    @Mapping(target = "createDate",ignore = true)
    @Mapping(target = "lastUpdatedDate",ignore = true)
    @Mapping(target = "newsId",ignore = true)
    void toAuthor(AuthorUpdateDto articleUpdateDto, @MappingTarget AuthorModel mappingResult);
     */
}
