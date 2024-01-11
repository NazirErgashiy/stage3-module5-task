package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.dao.CommentRepository;
import com.mjc.school.repository.impl.dao.NewsRepository;
import com.mjc.school.repository.impl.model.CommentModel;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.error.exceptions.NotFoundRuntimeException;
import com.mjc.school.service.mapper.CommentMapperImpl;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepositoryMock;

    @Mock
    private CommentMapperImpl commentMapperMock;

    @Mock
    private NewsRepository newsRepositoryMock;

    @InjectMocks
    private CommentService commentServiceMock;

    @Test
    void readAll() {
        List<CommentModel> entityOut = new ArrayList<>();
        List<CommentDto> dtoOut = new ArrayList<>();

        Mockito.when(commentRepositoryMock.readAll()).thenReturn(entityOut);
        Mockito.when(commentMapperMock.modelToDtoList(entityOut)).thenReturn(dtoOut);

        List<CommentDto> result = commentServiceMock.readAll();

        Mockito.verify(commentRepositoryMock).readAll();
        Mockito.verify(commentMapperMock).modelToDtoList(entityOut);
        assertThat(result, is(dtoOut));
    }

    @Test
    void readById() {
        final Long AUTHOR_ID = 123L;
        CommentModel author = new CommentModel();
        Optional<CommentModel> optionalArticle = Optional.of(author);
        CommentDto authorDto = new CommentDto();

        Mockito.when(commentRepositoryMock.readById(AUTHOR_ID)).thenReturn(optionalArticle);
        Mockito.when(commentRepositoryMock.existById(AUTHOR_ID)).thenReturn(true);
        Mockito.when(commentMapperMock.modelToDto(author)).thenReturn(authorDto);

        CommentDto result = commentServiceMock.readById(AUTHOR_ID);

        Mockito.verify(commentRepositoryMock).readById(AUTHOR_ID);
        Mockito.verify(commentMapperMock).modelToDto(author);
        assertThat(result, is(authorDto));
    }

    @Test
    void create() {
        CommentModel entityIn = CommentModel.builder().build();
        CommentModel entityOut = CommentModel.builder().build();

        CommentDto dtoIn = CommentDto.builder().build();
        CommentDto dtoOut = CommentDto.builder().build();

        Mockito.when(commentMapperMock.dtoToModel(dtoIn)).thenReturn(entityIn);
        Mockito.when(commentRepositoryMock.create(entityIn)).thenReturn(entityOut);
        Mockito.when(commentMapperMock.modelToDto(entityOut)).thenReturn(dtoOut);
        Mockito.when(newsRepositoryMock.existById(null)).thenReturn(true);

        CommentDto result = commentServiceMock.create(dtoIn);

        Mockito.verify(commentMapperMock).dtoToModel(dtoIn);
        Mockito.verify(commentRepositoryMock).create(entityIn);
        Mockito.verify(commentMapperMock).modelToDto(entityOut);
        Mockito.verify(newsRepositoryMock).existById(null);
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
        Mockito.when(commentRepositoryMock.existById(ID)).thenReturn(true);
        Mockito.when(commentRepositoryMock.deleteById(ID)).thenReturn(true);

        commentServiceMock.deleteById(ID);

        Mockito.verify(commentRepositoryMock).existById(ID);
        Mockito.verify(commentRepositoryMock).deleteById(ID);
    }

    @Test
    void deleteByIdNotFoundExpected() {
        final Long ID = 123L;
        Mockito.when(commentRepositoryMock.existById(ID)).thenReturn(false);

        try {
            commentServiceMock.deleteById(ID);

            fail("Exception should be thrown");
        } catch (NotFoundRuntimeException ex) {
            Mockito.verify(commentRepositoryMock).existById(ID);
            assertThat("Wrong message", ex.getMessage(), is("ERROR 404 Resource not found: Comment with id [" + ID + "] not found"));
        }
    }
}