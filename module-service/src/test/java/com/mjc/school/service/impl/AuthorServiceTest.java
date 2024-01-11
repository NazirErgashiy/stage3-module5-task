package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.dao.AuthorRepository;
import com.mjc.school.repository.impl.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.error.exceptions.NotFoundRuntimeException;
import com.mjc.school.service.mapper.AuthorMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepositoryMock;

    @Mock
    private AuthorMapperImpl authorMapperMock;

    @InjectMocks
    private AuthorService authorServiceMock;

    @Test
    void readAll() {
        List<AuthorModel> entityOut = new ArrayList<>();
        List<AuthorDto> dtoOut = new ArrayList<>();

        Mockito.when(authorRepositoryMock.readAll()).thenReturn(entityOut);
        Mockito.when(authorMapperMock.modelToDtoList(entityOut)).thenReturn(dtoOut);

        List<AuthorDto> result = authorServiceMock.readAll();

        Mockito.verify(authorRepositoryMock).readAll();
        Mockito.verify(authorMapperMock).modelToDtoList(entityOut);
        assertThat(result, is(dtoOut));
    }

    @Test
    void readById() {
        final Long AUTHOR_ID = 123L;
        AuthorModel author = new AuthorModel();
        Optional<AuthorModel> optionalArticle = Optional.of(author);
        AuthorDto authorDto = new AuthorDto();

        Mockito.when(authorRepositoryMock.readById(AUTHOR_ID)).thenReturn(optionalArticle);
        Mockito.when(authorRepositoryMock.existById(AUTHOR_ID)).thenReturn(true);
        Mockito.when(authorMapperMock.modelToDto(author)).thenReturn(authorDto);

        AuthorDto result = authorServiceMock.readById(AUTHOR_ID);

        Mockito.verify(authorRepositoryMock).readById(AUTHOR_ID);
        Mockito.verify(authorMapperMock).modelToDto(author);
        assertThat(result, is(authorDto));
    }

    @Test
    void create() {
        AuthorModel entityIn = AuthorModel.builder().build();
        AuthorModel entityOut = AuthorModel.builder().build();

        AuthorDto dtoIn = AuthorDto.builder().build();
        AuthorDto dtoOut = AuthorDto.builder().build();

        Mockito.when(authorMapperMock.dtoToModel(dtoIn)).thenReturn(entityIn);
        Mockito.when(authorRepositoryMock.create(entityIn)).thenReturn(entityOut);
        Mockito.when(authorMapperMock.modelToDto(entityOut)).thenReturn(dtoOut);

        AuthorDto result = authorServiceMock.create(dtoIn);

        Mockito.verify(authorMapperMock).dtoToModel(dtoIn);
        Mockito.verify(authorRepositoryMock).create(entityIn);
        Mockito.verify(authorMapperMock).modelToDto(entityOut);
        assertThat(result, is(dtoOut));
    }

    @Test
    void update() {
        /*
        AuthorModel entityIn = AuthorModel.builder().build();
        AuthorModel entityOut = AuthorModel.builder().build();

        Optional<AuthorModel> entityOutOptional = Optional.of(entityOut);

        AuthorUpdateDto dtoIn = AuthorUpdateDto.builder().build();
        AuthorDto dtoOut = AuthorDto.builder().build();

        Mockito.when(authorRepositoryMock.existById(null)).thenReturn(true);
        Mockito.when(authorMapperMock.toAuthor(dtoIn)).thenReturn(entityIn);
        Mockito.when(authorRepositoryMock.readById(null)).thenReturn(entityOutOptional);
        Mockito.when(authorRepositoryMock.update(entityIn)).thenReturn(entityOut);
        Mockito.when(authorMapperMock.modelToDto(entityOut)).thenReturn(dtoOut);

        AuthorDto result = authorServiceMock.update(dtoIn);

        Mockito.verify(authorRepositoryMock.existById(null));
        Mockito.verify(authorMapperMock).toAuthor(dtoIn);
        Mockito.verify(authorRepositoryMock).update(entityIn);
        Mockito.verify(authorMapperMock).modelToDto(entityOut);
        assertThat(result, is(dtoOut));
         */
    }

    @Test
    void deleteById() {
        final Long ID = 123L;
        Mockito.when(authorRepositoryMock.existById(ID)).thenReturn(true);
        Mockito.when(authorRepositoryMock.deleteById(ID)).thenReturn(true);

        authorServiceMock.deleteById(ID);

        Mockito.verify(authorRepositoryMock).existById(ID);
        Mockito.verify(authorRepositoryMock).deleteById(ID);
    }

    @Test
    void deleteByIdNotFoundExpected() {
        final Long ID = 123L;
        Mockito.when(authorRepositoryMock.existById(ID)).thenReturn(false);

        try {
            authorServiceMock.deleteById(ID);

            fail("Exception should be thrown");
        } catch (NotFoundRuntimeException ex) {
            Mockito.verify(authorRepositoryMock).existById(ID);
            assertThat("Wrong message", ex.getMessage(), is("ERROR 404 Resource not found: Author with id [" + ID + "] not found"));
        }
    }
}