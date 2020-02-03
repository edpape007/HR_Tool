package com.hrtool.repository;

import java.util.List;
import java.util.Optional;

/**
 * @author edpape
 * Interface to handle persistance operations.
 * @param <T> Object type to persist.
 */
public interface Repository<T> {
    List<T> findAll();

    Optional<T> findById(String id);

    void save(T model);
    void saveAll(List<T> models);
    void delete(T model);
    void update(T model);
}
