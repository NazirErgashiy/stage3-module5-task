package com.mjc.school.controller.impl;

import com.mjc.school.controller.ModifiedBaseController;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.CommentDto;
import com.mjc.school.service.dto.update.CommentUpdateDto;
import com.mjc.school.service.impl.CommentService;
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
@RequestMapping("/api/v1/news/{id}/comments")
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting comments in the application")
public class CommentController implements ModifiedBaseController<CommentUpdateDto, CommentDto, Long> {

    private final CommentService commentService;

    @ApiOperation(value = "Get comments", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                   @RequestParam(defaultValue = "50") Integer pageSize,
                                   @RequestParam(required = false ,defaultValue = "id asc") String sortBy) {
        return commentService.readAll(pageNumber, pageSize, sortBy);
    }

    @ApiOperation(value = "Get comment by id", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Comment not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @GetMapping("/{comment-id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getById(@PathVariable("comment-id") Long id) {
        return commentService.readById(id);
    }

    @ApiOperation(value = "Crete comment", response = CommentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment successfully created"),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "Comment not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@RequestBody @Validated CommentDto createRequest) {
        return commentService.create(createRequest);
    }

    @ApiOperation(value = "Update comment by id", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment successfully updated"),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "Comment not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CommentDto update(@PathVariable Long id, @RequestBody @Validated CommentUpdateDto updateRequest) {
        updateRequest.setNewsId(id);
        return commentService.update(updateRequest);
    }

    @ApiOperation(value = "Delete comment by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Comment successfully deleted"),
            @ApiResponse(code = 404, message = "Comment not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @DeleteMapping("/{comment-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("comment-id") Long id) {
        commentService.deleteById(id);
    }
}
