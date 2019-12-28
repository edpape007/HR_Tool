package com.hrtool.repository;

import com.hrtool.model.EmployeeRate;

import java.util.List;
import java.util.Optional;

public class MockRateRepository implements Repository<EmployeeRate> {
    @Override
    public List<EmployeeRate> findAll() {
        return null;
    }

    @Override
    public Optional<EmployeeRate> findById(String id) {
        return Optional.empty();
    }

    @Override
    public void save(EmployeeRate model) {

    }

    @Override
    public void saveAll(List<EmployeeRate> models) {

    }

    @Override
    public void delete(EmployeeRate model) {

    }

    @Override
    public void update(EmployeeRate model) {

    }
}
