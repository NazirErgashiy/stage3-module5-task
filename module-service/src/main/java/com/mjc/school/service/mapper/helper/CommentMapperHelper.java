package com.mjc.school.service.mapper.helper;

import com.mjc.school.repository.impl.model.NewsModel;
import org.springframework.stereotype.Component;

@Component
public class CommentMapperHelper {

    public Long map(NewsModel value) {
        return value.getId();
    }

    public NewsModel map(Long value) {
        NewsModel newsModel = new NewsModel();
        newsModel.setId(value);
        return newsModel;
    }
}
