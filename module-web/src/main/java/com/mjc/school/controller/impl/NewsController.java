package com.mjc.school.controller.impl;

import com.mjc.school.controller.ModifiedBaseController;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.dto.update.NewsUpdateDto;
import com.mjc.school.service.impl.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/news")
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting news in the application")
public class NewsController implements ModifiedBaseController<NewsUpdateDto, NewsDto, Long> {
    private final NewsService newsService;

    @ApiOperation(value = "Get news", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDto> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                @RequestParam(defaultValue = "50") Integer pageSize,
                                @RequestParam(required = false ,defaultValue = "id asc") String sortBy) {
        return newsService.readAll(pageNumber, pageSize, sortBy);
    }

    @ApiOperation(value = "Get news by id", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "News not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto getById(@PathVariable Long id) {
        return newsService.readById(id);
    }

    @ApiOperation(value = "Crete news", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News successfully created"),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "News not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto create(@RequestBody @Validated NewsDto createRequest) {
        return newsService.create(createRequest);
    }

    @ApiOperation(value = "Update news by id", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "News successfully updated"),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "News not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto update(@PathVariable Long id,@RequestBody @Validated NewsUpdateDto updateRequest) {
        updateRequest.setId(id);
        return newsService.update(updateRequest);
    }

    @ApiOperation(value = "Delete news by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "News successfully deleted"),
            @ApiResponse(code = 404, message = "News not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
    }

    @ApiOperation(value = "Get author by news id", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "OK"),
            @ApiResponse(code = 404, message = "Author not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @GetMapping("/{id}/author")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto getAuthorByNewsId(@PathVariable Long id) {
        return newsService.getAuthorByNewsId(id);
    }

    @ApiOperation(value = "Get tag by news id", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "OK"),
            @ApiResponse(code = 404, message = "Tag not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @GetMapping("/{id}/tag")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getTagsByNewsId(@PathVariable Long id) {
        return newsService.getTagsByNewsId(id);
    }

    /*
    public List<NewsDto> getNewsByParams(String tagNames, List<Long> tagIds, String authorName, String title, String content) {
        if ("-".equals(tagNames))
            tagNames = null;
        if (tagIds.get(0) == 0L)
            tagIds = null;
        if ("-".equals(authorName))
            authorName = null;
        if ("-".equals(title))
            title = null;
        if ("-".equals(content))
            content = null;
        return newsService.getNewsByParams(tagNames, tagIds, authorName, title, content);
    }
    */
}
