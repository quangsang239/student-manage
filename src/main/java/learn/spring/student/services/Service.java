package learn.spring.student.services;

import learn.spring.student.common.EntityResponse;

import java.util.List;

public interface Service<T> {
    EntityResponse<List<T>> findAll();

    EntityResponse<T> findById(Integer id);

    EntityResponse<T> create(T model);

    EntityResponse<T> delete(Integer id);
}
