package com.hrtool.repository;

import com.hrtool.model.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MockEmployeeRepository implements Repository<Employee> {
    private List<Employee> employees;

    public MockEmployeeRepository() {
        employees = new ArrayList<>();

        employees.add(new Employee("1234", "Dummy1", "DummyJob", Collections.EMPTY_LIST, null, Collections.EMPTY_LIST));
        employees.add(new Employee("12345", "Dummy2", "DummyJob2", Collections.EMPTY_LIST, null, Collections.EMPTY_LIST));
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Optional<Employee> findById(String id) {
        return findAll()
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(Employee model) {
    }

    @Override
    public void saveAll(List<Employee> models) {
    }

    @Override
    public void delete(Employee model) {
    }

    @Override
    public void update(Employee model) {
    }
}
