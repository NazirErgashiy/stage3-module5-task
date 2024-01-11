package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.dao.TagRepository;
import com.mjc.school.repository.impl.model.TagModel;
import com.mjc.school.service.ModifiedBaseService;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.dto.update.TagUpdateDto;
import com.mjc.school.service.error.exceptions.NotFoundRuntimeException;
import com.mjc.school.service.mapper.TagMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService implements ModifiedBaseService<TagUpdateDto, TagDto, Long> {

    private final TagRepository tagRepository;
    private final TagMapperImpl tagMapper;


    @Transactional(readOnly = true)
    @Override
    public List<TagDto> readAll() {
        return tagMapper.modelToDtoList(tagRepository.readAll());
    }

    @Transactional(readOnly = true)
    public List<TagDto> readAll(Integer pageNumber, Integer pageSize, String sortBy) {
        List<TagModel> readResult = tagRepository.readAll(pageNumber, pageSize, sortBy);
        return tagMapper.modelToDtoList(readResult);
    }

    @Transactional(readOnly = true)
    @Override
    public TagDto readById(Long id) {
        if (!tagRepository.existById(id)) {
            throw new NotFoundRuntimeException("Tag with id [" + id + "] not found");
        }
        return tagMapper.modelToDto(tagRepository.readById(id).get());
    }

    @Transactional
    @Override
    public TagDto create(TagDto createRequest) {
        TagModel sourceTag = tagMapper.dtoToModel(createRequest);
        TagModel newTag = tagRepository.create(sourceTag);
        return tagMapper.modelToDto(newTag);
    }

    @Transactional
    @Override
    public TagDto update(TagUpdateDto updateRequest) {
        if (!tagRepository.existById(updateRequest.getId())) {
            throw new NotFoundRuntimeException("Tag with id [" + updateRequest.getId() + "] not found");
        }
        TagModel sourceTag = tagMapper.toTag(updateRequest);
        TagModel updatedTag = tagRepository.update(sourceTag);
        return tagMapper.modelToDto(updatedTag);
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (tagRepository.existById(id)) {
            return tagRepository.deleteById(id);
        }
        throw new NotFoundRuntimeException("Tag with id [" + id + "] not found");
    }

    private TagDto createSpecialTag(String name) {
        TagDto aReq1 = new TagDto();
        aReq1.setName(name);
        return create(aReq1);
    }

    public void createTestDB() {

        createSpecialTag("crime");
        createSpecialTag("wizard");
        createSpecialTag("love");
        createSpecialTag("war");
        createSpecialTag("luck");
        createSpecialTag("sport");
        createSpecialTag("computer");
        createSpecialTag("games");
        createSpecialTag("java");
        createSpecialTag("heart");
    }
}
