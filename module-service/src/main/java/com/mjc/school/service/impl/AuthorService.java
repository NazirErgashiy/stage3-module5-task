package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.dao.AuthorRepository;
import com.mjc.school.repository.impl.model.AuthorModel;
import com.mjc.school.service.ModifiedBaseService;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.update.AuthorUpdateDto;
import com.mjc.school.service.error.exceptions.NotFoundRuntimeException;
import com.mjc.school.service.mapper.AuthorMapperImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements ModifiedBaseService<AuthorUpdateDto, AuthorDto, Long> {

    private final AuthorRepository authorRepository;
    private final AuthorMapperImpl authorMapper;

    @Transactional(readOnly = true)
    @Override
    public List<AuthorDto> readAll() {
        return authorMapper.modelToDtoList(authorRepository.readAll());
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> readAll(Integer pageNumber, Integer pageSize, String sortBy) {
        List<AuthorModel> readResult = authorRepository.readAll(pageNumber, pageSize, sortBy);
        return authorMapper.modelToDtoList(readResult);
    }

    @Transactional(readOnly = true)
    @Override
    public AuthorDto readById(Long id) {
        if (!authorRepository.existById(id)) {
            throw new NotFoundRuntimeException("Author with id [" + id + "] not found");
        }
        return authorMapper.modelToDto(authorRepository.readById(id).get());
    }

    @Transactional
    @Override
    public AuthorDto create(AuthorDto createRequest) {
        AuthorModel sourceAuthor = authorMapper.dtoToModel(createRequest);
        AuthorModel newAuthor = authorRepository.create(sourceAuthor);
        return authorMapper.modelToDto(newAuthor);
    }

    @Transactional
    @Override
    public AuthorDto update(AuthorUpdateDto updateRequest) {
        if (!authorRepository.existById(updateRequest.getId())) {
            throw new NotFoundRuntimeException("Author with id [" + updateRequest.getId() + "] not found");
        }
        AuthorModel sourceAuthor = authorMapper.toAuthor(updateRequest);
        AuthorModel updatedAuthor = authorRepository.update(sourceAuthor);
        return authorMapper.modelToDto(updatedAuthor);
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (authorRepository.existById(id)) {
            return authorRepository.deleteById(id);
        }
        throw new NotFoundRuntimeException("Author with id [" + id + "] not found");
    }

    private AuthorDto createSpecialAuthor(String name) {
        AuthorDto create = new AuthorDto();
        create.setName(name);
        return create(create);
    }

    public void createTestDB() {

        createSpecialAuthor("Laila Mcmahon");
        createSpecialAuthor("Kaleb Proctor");
        createSpecialAuthor("Livia Moody");
        createSpecialAuthor("Corey Terry");
        createSpecialAuthor("Charlie West");
        createSpecialAuthor("Cleo Rush");
        createSpecialAuthor("Kamran Wolf");
        createSpecialAuthor("Elissa Swee");
        createSpecialAuthor("Alys Hines");
        createSpecialAuthor("Tiffany Ber");
        createSpecialAuthor("Lowri Ortiz");
        createSpecialAuthor("Kelsey Gal");
        createSpecialAuthor("Darren Salas");
        createSpecialAuthor("Jeremy Eaton");
        createSpecialAuthor("Simon Summers");
        createSpecialAuthor("Julian Gibbons");
        createSpecialAuthor("Nellie Poole");
        createSpecialAuthor("Hazel Murray");
        createSpecialAuthor("Cohen Holmes");
        createSpecialAuthor("Serena Buch");
    }
}
