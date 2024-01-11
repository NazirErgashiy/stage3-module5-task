package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.dao.CommentRepository;
import com.mjc.school.repository.impl.dao.NewsRepository;
import com.mjc.school.repository.impl.model.CommentModel;
import com.mjc.school.service.ModifiedBaseService;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.dto.update.CommentUpdateDto;
import com.mjc.school.service.error.exceptions.NotFoundRuntimeException;
import com.mjc.school.service.mapper.CommentMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ModifiedBaseService<CommentUpdateDto, CommentDto, Long> {

    private final CommentRepository commentRepository;
    private final CommentMapperImpl commentMapper;
    private final NewsRepository newsRepository;

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> readAll() {
        return commentMapper.modelToDtoList(commentRepository.readAll());
    }

    @Transactional(readOnly = true)
    public List<CommentDto> readAll(Integer pageNumber, Integer pageSize, String sortBy) {
        List<CommentModel> readResult = commentRepository.readAll(pageNumber, pageSize, sortBy);
        return commentMapper.modelToDtoList(readResult);
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto readById(Long id) {
        if (!commentRepository.existById(id)) {
            throw new NotFoundRuntimeException("Comment with id [" + id + "] not found");
        }
        return commentMapper.modelToDto(commentRepository.readById(id).get());
    }

    @Transactional
    @Override
    public CommentDto create(CommentDto createRequest) {
        if (!newsRepository.existById(createRequest.getNewsId())) {
            throw new NotFoundRuntimeException("News with id [" + createRequest.getId() + "] not found");
        }
        CommentModel sourceAuthor = commentMapper.dtoToModel(createRequest);
        CommentModel newComment = commentRepository.create(sourceAuthor);
        return commentMapper.modelToDto(newComment);
    }

    @Transactional
    public CommentDto create(Long newsId, CommentDto createRequest) {
        if (!newsRepository.existById(newsId)) {
            throw new NotFoundRuntimeException("News with id [" + newsId + "] not found");
        }
        createRequest.setNewsId(newsId);
        CommentModel sourceAuthor = commentMapper.dtoToModel(createRequest);
        CommentModel newComment = commentRepository.create(sourceAuthor);
        return commentMapper.modelToDto(newComment);
    }

    @Transactional
    @Override
    public CommentDto update(CommentUpdateDto updateRequest) {
        if (!commentRepository.existById(updateRequest.getId())) {
            throw new NotFoundRuntimeException("Comment with id [" + updateRequest.getId() + "] not found");
        }
        CommentModel sourceComment = commentMapper.toComment(updateRequest);
        CommentModel updatedComment = commentRepository.update(sourceComment);
        return commentMapper.modelToDto(updatedComment);
    }

    @Override
    public boolean deleteById(Long id) {
        if (commentRepository.existById(id)) {
            return commentRepository.deleteById(id);
        }
        throw new NotFoundRuntimeException("Comment with id [" + id + "] not found");
    }

    private CommentDto createSpecialComment(String content, Long newsId) {
        CommentDto commentRequest = new CommentDto();
        commentRequest.setContent(content);
        commentRequest.setNewsId(newsId);
        return create(commentRequest);
    }

    public void createTestDB() {
        createSpecialComment("Very good news", 1L);
        createSpecialComment("Very good news", 1L);
        createSpecialComment("Very good news", 1L);
        createSpecialComment("Very good news", 1L);
    }
}
