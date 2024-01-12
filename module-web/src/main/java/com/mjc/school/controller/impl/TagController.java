package com.mjc.school.controller.impl;

import com.mjc.school.controller.ModifiedBaseController;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.TagDto;
import com.mjc.school.service.dto.update.TagUpdateDto;
import com.mjc.school.service.impl.TagService;
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
@RequestMapping("/api/v1/tags")
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting tags in the application")
public class TagController implements ModifiedBaseController<TagUpdateDto, TagDto, Long> {

    private final TagService tagService;

    @ApiOperation(value = "Get tags", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                               @RequestParam(defaultValue = "50") Integer pageSize,
                               @RequestParam(required = false ,defaultValue = "id asc") String sortBy) {
        return tagService.readAll(pageNumber, pageSize, sortBy);
    }

    @ApiOperation(value = "Get tag by id", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Tag not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto getById(@PathVariable Long id) {
        return tagService.readById(id);
    }

    @ApiOperation(value = "Crete tag", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Tag successfully created"),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "Tag not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody @Validated TagDto createRequest) {
        return tagService.create(createRequest);
    }

    @ApiOperation(value = "Update tag by id", response = NewsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tag successfully updated"),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "Tag not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto update(@PathVariable Long id,@RequestBody @Validated TagUpdateDto updateRequest) {
        updateRequest.setId(id);
        return tagService.update(updateRequest);
    }

    @ApiOperation(value = "Delete tag by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Tag successfully deleted"),
            @ApiResponse(code = 404, message = "Tag not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tagService.deleteById(id);
    }
}
