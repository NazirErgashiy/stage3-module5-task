package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.model.AuthorModel;
import com.mjc.school.repository.impl.model.CommentModel;
import com.mjc.school.repository.impl.model.NewsModel;
import com.mjc.school.repository.impl.model.TagModel;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.update.NewsUpdateDto;
import com.mjc.school.service.mapper.helper.NewsMapperHelper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class NewsMapperTest {

    private final NewsMapperHelper newsMapperHelper = new NewsMapperHelper();
    private final NewsMapper newsMapper = new NewsMapperImpl(newsMapperHelper);

    @Test
    void modelToDto() {
        NewsModel source = createBasicModel();

        var result = newsMapper.modelToDto(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getTitle(), result.getTitle());
        assertEquals(source.getContent(), result.getContent());
        assertEquals(source.getAuthor().getId(), result.getAuthorId());
        for (int i = 0; i < source.getTags().size(); i++) {
            assertEquals(source.getTags().get(i).getId(), result.getTagsId().get(i));
        }
        for (int i = 0; i < source.getComments().size(); i++) {
            assertEquals(source.getComments().get(i).getId(), result.getCommentsId().get(i));
        }
    }

    @Test
    void dtoToModel() {
        NewsDto source = createBasicDto();

        var result = newsMapper.dtoToModel(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getTitle(), result.getTitle());
        assertEquals(source.getContent(), result.getContent());
        assertEquals(source.getAuthorId(), result.getAuthor().getId());
        for (int i = 0; i < source.getTagsId().size(); i++) {
            assertEquals(source.getTagsId().get(i), result.getTags().get(i).getId());
        }
        for (int i = 0; i < source.getCommentsId().size(); i++) {
            assertEquals(source.getCommentsId().get(i), result.getComments().get(i).getId());
        }
    }

    @Test
    void updateDtoToModel() {
        NewsUpdateDto source = createBasicUpdateDto();
        NewsModel result = newsMapper.toNews(source);

        assertEquals(source.getId(), result.getId());
        assertEquals(source.getTitle(), result.getTitle());
        assertEquals(source.getContent(), result.getContent());
        assertNull(result.getAuthor());
        for (int i = 0; i < source.getTagsId().size(); i++) {
            assertEquals(source.getTagsId().get(i), result.getTags().get(i).getId());
        }
    }

    private NewsModel createBasicModel() {
        NewsModel result = new NewsModel();
        result.setId(1L);
        result.setTitle("testTitle");
        result.setContent("testContent");

        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(2L);
        result.setAuthor(authorModel);

        TagModel tagModel = new TagModel();
        tagModel.setId(3L);
        List<TagModel> tagsList = new ArrayList<>();
        tagsList.add(tagModel);
        result.setTags(tagsList);

        CommentModel commentModel = new CommentModel();
        commentModel.setId(4L);
        List<CommentModel> commentsList = new ArrayList<>();
        commentsList.add(commentModel);
        result.setComments(commentsList);
        return result;
    }

    private NewsDto createBasicDto() {
        NewsDto result = new NewsDto();
        result.setId(1L);
        result.setTitle("testTitle");
        result.setContent("testContent");
        result.setAuthorId(2L);

        List<Long> tagsList = new ArrayList<>();
        tagsList.add(3L);
        result.setTagsId(tagsList);

        List<Long> commentsList = new ArrayList<>();
        commentsList.add(4L);
        result.setCommentsId(commentsList);
        return result;
    }

    private NewsUpdateDto createBasicUpdateDto() {
        NewsUpdateDto result = new NewsUpdateDto();
        result.setId(1L);
        result.setTitle("testTitle");
        result.setContent("testContent");

        List<Long> tagsList = new ArrayList<>();
        tagsList.add(3L);
        result.setTagsId(tagsList);
        return result;
    }
}