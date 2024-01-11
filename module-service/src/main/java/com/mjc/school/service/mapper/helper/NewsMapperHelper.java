package com.mjc.school.service.mapper.helper;

import com.mjc.school.repository.impl.model.AuthorModel;
import com.mjc.school.repository.impl.model.CommentModel;
import com.mjc.school.repository.impl.model.TagModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapperHelper {
    public AuthorModel map(Long value) {
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(value);
        return authorModel;
    }

    public List<TagModel> map(List<Long> value) {
        if (value == null) return new ArrayList<>();
        List<TagModel> result = new ArrayList<>();
        for (Long aLong : value) {
            TagModel newTagModel = new TagModel();
            newTagModel.setId(aLong);
            result.add(newTagModel);
        }
        return result;
    }

    public List<Long> map1(List<CommentModel> value) {
        if (value == null) return new ArrayList<>();
        List<Long> result = new ArrayList<>();
        for (CommentModel commentModel : value) {
            result.add(commentModel.getId());
        }
        return result;
    }

    public List<CommentModel> map2(List<Long> value) {
        if (value == null) return new ArrayList<>();
        List<CommentModel> result = new ArrayList<>();
        for (Long aLong : value) {
            CommentModel newCommentModel = new CommentModel();
            newCommentModel.setId(aLong);
            result.add(newCommentModel);
        }
        return result;
    }

    public Long map(AuthorModel value) {
        return value.getId();
    }

    public List<Long> map3(List<TagModel> value) {
        if (value == null) return new ArrayList<>();
        List<Long> result = new ArrayList<>();
        for (TagModel tagModel : value) {
            result.add(tagModel.getId());
        }
        return result;
    }
}
