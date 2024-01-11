package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.dao.TagRepository;
import com.mjc.school.repository.impl.model.TagModel;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.error.exceptions.NotFoundRuntimeException;
import com.mjc.school.service.mapper.TagMapperImpl;
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
class TagServiceTest {

    @Mock
    private TagRepository tagRepositoryMock;

    @Mock
    private TagMapperImpl tagMapperMock;

    @InjectMocks
    private TagService tagServiceMock;

    @Test
    void readAll() {
        List<TagModel> entityOut = new ArrayList<>();
        List<TagDto> dtoOut = new ArrayList<>();

        Mockito.when(tagRepositoryMock.readAll()).thenReturn(entityOut);
        Mockito.when(tagMapperMock.modelToDtoList(entityOut)).thenReturn(dtoOut);

        List<TagDto> result = tagServiceMock.readAll();

        Mockito.verify(tagRepositoryMock).readAll();
        Mockito.verify(tagMapperMock).modelToDtoList(entityOut);
        assertThat(result, is(dtoOut));
    }

    @Test
    void readById() {
        final Long AUTHOR_ID = 123L;
        TagModel author = new TagModel();
        Optional<TagModel> optionalArticle = Optional.of(author);
        TagDto authorDto = new TagDto();

        Mockito.when(tagRepositoryMock.readById(AUTHOR_ID)).thenReturn(optionalArticle);
        Mockito.when(tagRepositoryMock.existById(AUTHOR_ID)).thenReturn(true);
        Mockito.when(tagMapperMock.modelToDto(author)).thenReturn(authorDto);

        TagDto result = tagServiceMock.readById(AUTHOR_ID);

        Mockito.verify(tagRepositoryMock).readById(AUTHOR_ID);
        Mockito.verify(tagMapperMock).modelToDto(author);
        assertThat(result, is(authorDto));
    }

    @Test
    void create() {
        TagModel entityIn = TagModel.builder().build();
        TagModel entityOut = TagModel.builder().build();

        TagDto dtoIn = TagDto.builder().build();
        TagDto dtoOut = TagDto.builder().build();

        Mockito.when(tagMapperMock.dtoToModel(dtoIn)).thenReturn(entityIn);
        Mockito.when(tagRepositoryMock.create(entityIn)).thenReturn(entityOut);
        Mockito.when(tagMapperMock.modelToDto(entityOut)).thenReturn(dtoOut);

        TagDto result = tagServiceMock.create(dtoIn);

        Mockito.verify(tagMapperMock).dtoToModel(dtoIn);
        Mockito.verify(tagRepositoryMock).create(entityIn);
        Mockito.verify(tagMapperMock).modelToDto(entityOut);
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
        Mockito.when(tagRepositoryMock.existById(ID)).thenReturn(true);
        Mockito.when(tagRepositoryMock.deleteById(ID)).thenReturn(true);

        tagServiceMock.deleteById(ID);

        Mockito.verify(tagRepositoryMock).existById(ID);
        Mockito.verify(tagRepositoryMock).deleteById(ID);
    }

    @Test
    void deleteByIdNotFoundExpected() {
        final Long ID = 123L;
        Mockito.when(tagRepositoryMock.existById(ID)).thenReturn(false);

        try {
            tagServiceMock.deleteById(ID);

            fail("Exception should be thrown");
        } catch (NotFoundRuntimeException ex) {
            Mockito.verify(tagRepositoryMock).existById(ID);
            assertThat("Wrong message", ex.getMessage(), is("ERROR 404 Resource not found: Tag with id [" + ID + "] not found"));
        }
    }
}