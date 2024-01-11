package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.dao.AuthorRepository;
import com.mjc.school.repository.impl.dao.NewsRepository;
import com.mjc.school.repository.impl.model.NewsModel;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.error.exceptions.NotFoundRuntimeException;
import com.mjc.school.service.mapper.NewsMapperImpl;
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
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepositoryMock;

    @Mock
    private NewsMapperImpl newsMapperMock;

    @Mock
    private AuthorRepository authorRepositoryMock;

    @InjectMocks
    private NewsService newsServiceMock;

    @Test
    void readAll() {
        List<NewsModel> entityOut = new ArrayList<>();
        List<NewsDto> dtoOut = new ArrayList<>();

        Mockito.when(newsRepositoryMock.readAll()).thenReturn(entityOut);
        Mockito.when(newsMapperMock.modelToDtoList(entityOut)).thenReturn(dtoOut);

        List<NewsDto> result = newsServiceMock.readAll();

        Mockito.verify(newsRepositoryMock).readAll();
        Mockito.verify(newsMapperMock).modelToDtoList(entityOut);
        assertThat(result, is(dtoOut));
    }

    @Test
    void readById() {
        final Long AUTHOR_ID = 123L;
        NewsModel author = new NewsModel();
        Optional<NewsModel> optionalArticle = Optional.of(author);
        NewsDto authorDto = new NewsDto();

        Mockito.when(newsRepositoryMock.readById(AUTHOR_ID)).thenReturn(optionalArticle);
        Mockito.when(newsRepositoryMock.existById(AUTHOR_ID)).thenReturn(true);
        Mockito.when(newsMapperMock.modelToDto(author)).thenReturn(authorDto);

        NewsDto result = newsServiceMock.readById(AUTHOR_ID);

        Mockito.verify(newsRepositoryMock).readById(AUTHOR_ID);
        Mockito.verify(newsMapperMock).modelToDto(author);
        assertThat(result, is(authorDto));
    }

    @Test
    void create() {
        NewsModel entityIn = NewsModel.builder().build();
        NewsModel entityOut = NewsModel.builder().build();

        NewsDto dtoIn = NewsDto.builder().build();
        NewsDto dtoOut = NewsDto.builder().build();

        Mockito.when(newsMapperMock.dtoToModel(dtoIn)).thenReturn(entityIn);
        Mockito.when(newsRepositoryMock.create(entityIn)).thenReturn(entityOut);
        Mockito.when(newsMapperMock.modelToDto(entityOut)).thenReturn(dtoOut);
        Mockito.when(authorRepositoryMock.existById(null)).thenReturn(true);

        NewsDto result = newsServiceMock.create(dtoIn);

        Mockito.verify(newsMapperMock).dtoToModel(dtoIn);
        Mockito.verify(newsRepositoryMock).create(entityIn);
        Mockito.verify(newsMapperMock).modelToDto(entityOut);
        Mockito.verify(authorRepositoryMock).existById(null);
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
        Mockito.when(newsRepositoryMock.existById(ID)).thenReturn(true);
        Mockito.when(newsRepositoryMock.deleteById(ID)).thenReturn(true);

        newsServiceMock.deleteById(ID);

        Mockito.verify(newsRepositoryMock).existById(ID);
        Mockito.verify(newsRepositoryMock).deleteById(ID);
    }

    @Test
    void deleteByIdNotFoundExpected() {
        final Long ID = 123L;
        Mockito.when(newsRepositoryMock.existById(ID)).thenReturn(false);

        try {
            newsServiceMock.deleteById(ID);

            fail("Exception should be thrown");
        } catch (NotFoundRuntimeException ex) {
            Mockito.verify(newsRepositoryMock).existById(ID);
            assertThat("Wrong message", ex.getMessage(), is("ERROR 404 Resource not found: News with id [" + ID + "] not found"));
        }
    }


    /*
    @Test
    void createReadByIdDelete() {
        AuthorDto authorDto1 = createBasicAuthor();
        NewsDto newsDto1 = createBasicNews(authorDto1.getId());

        assertEquals(newsDto1, newsService.readById(newsDto1.getId()));
        assertEquals(authorDto1, authorService.readById(authorDto1.getId()));

        authorService.deleteById(authorDto1.getId());

        assertThrows(AuthorNotFoundRuntimeException.class, () -> authorService.readById(authorDto1.getId()));
        assertThrows(NewsNotFoundRuntimeException.class, () -> newsService.readById(newsDto1.getId()));
    }
     */
}