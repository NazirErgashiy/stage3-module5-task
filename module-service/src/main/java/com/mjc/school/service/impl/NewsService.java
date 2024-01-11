package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.dao.AuthorRepository;
import com.mjc.school.repository.impl.dao.NewsRepository;
import com.mjc.school.repository.impl.dao.TagRepository;
import com.mjc.school.repository.impl.model.AuthorModel;
import com.mjc.school.repository.impl.model.NewsModel;
import com.mjc.school.service.ModifiedBaseService;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.dto.update.NewsUpdateDto;
import com.mjc.school.service.error.exceptions.NotFoundRuntimeException;
import com.mjc.school.service.mapper.NewsMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Component
public class NewsService implements ModifiedBaseService<NewsUpdateDto, NewsDto, Long> {

    private final NewsRepository newsRepository;
    private final NewsMapperImpl newsMapper;
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;
    private final TagService tagService;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    @Override
    public List<NewsDto> readAll() {
        return newsMapper.modelToDtoList(newsRepository.readAll());
    }

    @Transactional(readOnly = true)
    public List<NewsDto> readAll(Integer pageNumber, Integer pageSize, String sortBy) {
        List<NewsModel> readResult = newsRepository.readAll(pageNumber, pageSize, sortBy);
        return newsMapper.modelToDtoList(readResult);
    }

    @Transactional(readOnly = true)
    @Override
    public NewsDto readById(Long id) {
        if (!newsRepository.existById(id)) {
            throw new NotFoundRuntimeException("News with id [" + id + "] not found");
        }
        return newsMapper.modelToDto(newsRepository.readById(id).get());
    }

    @Transactional
    @Override
    public NewsDto create(NewsDto createRequest) {
        if (!authorRepository.existById(createRequest.getAuthorId())) {
            throw new NotFoundRuntimeException("Author with id [" + createRequest.getAuthorId() + "] not found");
        }
        if (createRequest.getTagsId() != null) {
            List<Long> tagsId = createRequest.getTagsId();
            for (int i = 0; i < tagsId.size(); i++) {
                if (!tagRepository.existById(tagsId.get(i))) {
                    throw new NotFoundRuntimeException("Tag with id [" + tagsId.get(i) + "] not found");
                }
            }
        }
        NewsModel sourceNews = newsMapper.dtoToModel(createRequest);
        NewsModel newNews = newsRepository.create(sourceNews);
        return newsMapper.modelToDto(newNews);
    }

    @Transactional
    @Override
    public NewsDto update(NewsUpdateDto updateRequest) {
        if (!newsRepository.existById(updateRequest.getId())) {
            throw new NotFoundRuntimeException("News with id [" + updateRequest.getId() + "] not found");
        }
        NewsModel sourceNews = newsMapper.toNews(updateRequest);

        AuthorModel authorModel=new AuthorModel();
        authorModel.setId(readById(updateRequest.getId()).getAuthorId());
        sourceNews.setAuthor(authorModel);

        NewsModel updatedNews = newsRepository.update(sourceNews);
        return newsMapper.modelToDto(updatedNews);
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (newsRepository.existById(id)) {
            return newsRepository.deleteById(id);
        }
        throw new NotFoundRuntimeException("News with id [" + id + "] not found");
    }

    @Transactional(readOnly = true)
    public AuthorDto getAuthorByNewsId(Long id) {
        return authorService.readById(readById(id).getId());
    }

    @Transactional(readOnly = true)
    public List<TagDto> getTagsByNewsId(Long id) {
        List<TagDto> result = new ArrayList<>();

        List<Long> tagsId = readById(id).getTagsId();
        for (Long aLong : tagsId) {
            result.add(tagService.readById(aLong));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<NewsDto> getNewsByParams(String tagNames, List<Long> tagIds, String authorName, String title, String content) {
        return newsMapper.modelToDtoList(newsRepository.getNewsByParams(tagNames, tagIds, authorName, title, content));
    }

    public void createTestDB() {
        List<Long> tagsList1 = new ArrayList<>();
        tagsList1.add(1L);
        tagsList1.add(2L);

        List<Long> tagsList2 = new ArrayList<>();
        tagsList2.add(4L);
        tagsList2.add(5L);
        tagsList2.add(7L);

        List<Long> commentsList = new ArrayList<>();
        commentsList.add(1L);
        commentsList.add(2L);
        commentsList.add(3L);
        commentsList.add(4L);

        createSpecialNews("Lord Of Dawn", "Now is the winter of our discontent", 1L, tagsList1, commentsList);
        createSpecialNews("Heir Of The Ancients", "Made glorious summer by this sun of York;", 1L, tagsList1, commentsList);
        createSpecialNews("Pirates Of The Ancestors", "And all the clouds that lour'd upon our house", 3L, tagsList2, commentsList);
        createSpecialNews("Rebels Of Earth", "In the deep bosom of the ocean buried.", 4L, tagsList1, new ArrayList<>());
        createSpecialNews("Kings And Mice", "Now are our brows bound with victorious wreaths;", 5L, new ArrayList<>(), commentsList);
        createSpecialNews("Gods And Priests", "Our bruised arms hung up for monuments;", 6L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Goal Of The Lost Ones", "Our stern alarums changed to merry meetings,", 7L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("End Of Nightmares", "Our dreadful marches to delightful measures.", 8L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Begging In The Future", "Grim-visaged war hath smooth'd his wrinkled front;", 9L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Flying Into The North", "I, that am curtail'd of this fair proportion,", 10L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Hero Of Dawn", "Creator Of Tomorrow", 11L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Human With Honor", "Priestess Without Desire", 12L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Strangers Of The Night", "Giants Of The Light", 13L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Officers With Vigor", "Boys Without Courage", 14L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Serpents And Companions", "Lords And Knights", 15L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Horses And Foreigners", "Wolves And Aliens", 16L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Cause Of Tomorrow", "Planet Of Water", 17L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Destiny Without Time", "Success Of The Void", 18L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Healing My Nightmares", "Clinging To The Future", 19L, new ArrayList<>(), new ArrayList<>());
        createSpecialNews("Choking In Eternity", "Hurt By Nightmares", 20L, new ArrayList<>(), new ArrayList<>());
    }

    private NewsDto createSpecialNews(String title, String content, Long authorId, List<Long> tagsId, List<Long> commentsId) {
        NewsDto request1 = new NewsDto();
        request1.setTitle(title);
        request1.setContent(content);
        request1.setAuthorId(authorId);
        request1.setTagsId(tagsId);
        //request1.setCommentsId(commentsId);
        return create(request1);
    }
}
