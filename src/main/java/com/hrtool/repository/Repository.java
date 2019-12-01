package com.hrtool.repository;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();

    T findById(String id);

    void save(T model);
    void saveAll(List<T> models);
    void delete(T model);
    void update(T model);
}
