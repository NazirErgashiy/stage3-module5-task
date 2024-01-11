package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.update.AuthorUpdateDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorMapperTest {

    AuthorMapperImpl authorMapper = new AuthorMapperImpl();

    @Test
    void modelToDto() {
        AuthorModel source = createBasicModel();
        var result = authorMapper.modelToDto(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getName(), result.getName());
    }

    @Test
    void dtoToModel() {
        AuthorDto source = createBasicDto();
        var result = authorMapper.dtoToModel(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getName(), result.getName());
    }

    @Test
    void updateDtoToModel() {
        AuthorUpdateDto source = createBasicUpdateDto();
        AuthorModel result = authorMapper.toAuthor(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getName(), result.getName());
    }

    private AuthorModel createBasicModel() {
        AuthorModel result = new AuthorModel();
        result.setId(1L);
        result.setName("testName");
        return result;
    }

    private AuthorDto createBasicDto() {
        AuthorDto result = new AuthorDto();
        result.setId(1L);
        result.setName("testName");
        return result;
    }

    private AuthorUpdateDto createBasicUpdateDto() {
        AuthorUpdateDto result = new AuthorUpdateDto();
        result.setId(1L);
        result.setName("testName");
        return result;
    }
}