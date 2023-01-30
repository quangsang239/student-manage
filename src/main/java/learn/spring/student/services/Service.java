package learn.spring.student.services;

import learn.spring.student.common.EntityResponse;

import java.util.List;

public interface Service<T> {
    List<T> findAll();

    T findById(Integer id);

    T create(T model);

    T delete(Integer id);
}
