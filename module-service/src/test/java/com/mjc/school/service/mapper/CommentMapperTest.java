package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.model.CommentModel;
import com.mjc.school.repository.impl.model.NewsModel;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.dto.update.CommentUpdateDto;
import com.mjc.school.service.mapper.helper.CommentMapperHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentMapperTest {

    CommentMapperHelper commentMapperHelper = new CommentMapperHelper();
    CommentMapperImpl commentMapper = new CommentMapperImpl(commentMapperHelper);

    @Test
    void modelToDto() {
        CommentModel source = createBasicModel();
        var result = commentMapper.modelToDto(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getContent(), result.getContent());
        assertEquals(source.getNews().getId(), result.getNewsId());
    }

    @Test
    void dtoToModel() {
        CommentDto source = createBasicDto();
        var result = commentMapper.dtoToModel(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getContent(), result.getContent());
        assertEquals(source.getNewsId(), result.getNews().getId());
    }

    @Test
    void updateDtoToModel() {
        CommentUpdateDto source = createBasicUpdateDto();
        CommentModel result = commentMapper.toComment(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getContent(), result.getContent());
        assertEquals(source.getNewsId(), result.getNews().getId());
    }

    private CommentDto createBasicDto() {
        CommentDto result = new CommentDto();
        result.setId(1L);
        result.setContent("testName");
        result.setNewsId(2L);
        return result;
    }

    private CommentModel createBasicModel() {
        CommentModel result = new CommentModel();
        result.setId(1L);
        result.setContent("testName");
        NewsModel news = new NewsModel();
        news.setId(2L);
        result.setNews(news);
        return result;
    }

    private CommentUpdateDto createBasicUpdateDto() {
        CommentUpdateDto result = new CommentUpdateDto();
        result.setId(1L);
        result.setContent("testName");
        result.setNewsId(2L);
        return result;
    }
}