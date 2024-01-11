package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.model.TagModel;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.dto.update.TagUpdateDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagMapperTest {

    TagMapperImpl tagMapper = new TagMapperImpl();

    @Test
    void modelToDto() {
        TagModel source = createBasicModel();
        var result = tagMapper.modelToDto(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getName(), result.getName());
    }

    @Test
    void dtoToModel() {
        TagDto source = createBasicDto();
        var result = tagMapper.dtoToModel(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getName(), result.getName());
    }

    @Test
    void updateDtoToModel() {
        TagUpdateDto source = createBasicUpdateDto();
        TagModel result = tagMapper.toTag(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getName(), result.getName());
    }

    private TagModel createBasicModel() {
        TagModel result = new TagModel();
        result.setId(1L);
        result.setName("testName");
        return result;
    }

    private TagDto createBasicDto() {
        TagDto result = new TagDto();
        result.setId(1L);
        result.setName("testName");
        return result;
    }

    private TagUpdateDto createBasicUpdateDto() {
        TagUpdateDto result = new TagUpdateDto();
        result.setId(1L);
        result.setName("testName");
        return result;
    }
}