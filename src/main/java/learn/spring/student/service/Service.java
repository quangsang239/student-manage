package learn.spring.student.service;

import java.util.List;

public interface Service<T> {
    List<T> findAll();

    T findById(Integer id);

    void create(T model);

    void delete(Integer id);
}
