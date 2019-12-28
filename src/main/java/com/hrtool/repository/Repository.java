package com.hrtool.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    List<T> findAll();

    Optional<T> findById(String id);

    void save(T model);
    void saveAll(List<T> models);
    void delete(T model);
    void update(T model);
}
