package com.mjc.school.controller;

import java.util.List;

public interface ModifiedBaseController<T, R, K> {

    List<R> getAll(Integer pageNumber, Integer pageSize, String sortBy);

    R getById(K id);

    R create(R createRequest);

    R update(K id, T updateRequest);

    void deleteById(K id);
}
