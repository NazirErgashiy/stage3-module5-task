package com.mjc.school.controller.impl;

import com.mjc.school.controller.ModifiedBaseController;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.update.AuthorUpdateDto;
import com.mjc.school.service.impl.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/authors")
@Api(produces = "application/json", value = "Operations for creating, updating, retrieving and deleting authors in the application")
public class AuthorController implements ModifiedBaseController<AuthorUpdateDto, AuthorDto, Long> {

    private final AuthorService authorService;

    @ApiOperation(value = "Get authors", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDto> getAll(@RequestParam(defaultValue = "1") Integer pageNumber,
                                  @RequestParam(defaultValue = "50") Integer pageSize,
                                  @RequestParam(required = false, defaultValue = "id asc") String sortBy) {
        return authorService.readAll(pageNumber, pageSize, sortBy);
    }

    @ApiOperation(value = "Get author by id", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Author not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto getById(@PathVariable Long id) {
        return authorService.readById(id);
    }

    @ApiOperation(value = "Crete author", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Author successfully created"),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "Author not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@RequestBody @Validated AuthorDto createRequest) {
        return authorService.create(createRequest);
    }

    @ApiOperation(value = "Update author by id", response = AuthorDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Author successfully updated"),
            @ApiResponse(code = 400, message = "Validation exception"),
            @ApiResponse(code = 404, message = "Author not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto update(@PathVariable Long id, @RequestBody @Validated AuthorUpdateDto updateRequest) {
        updateRequest.setId(id);
        return authorService.update(updateRequest);
    }

    @ApiOperation(value = "Delete author by id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Author successfully deleted"),
            @ApiResponse(code = 404, message = "Author not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        authorService.deleteById(id);
    }
}
