package com.hrtool.service;

import com.hrtool.model.Employee;
import com.hrtool.model.EmployeeBuilder;
import com.hrtool.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BossService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public BossService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findMyPossibleBosses(String employeeId) {
        return employeeRepository.findAll()
                .stream()
                .filter(item -> !item.getId().equals(employeeId))
                .collect(Collectors.toList());
    }

    public void associateEmployee(String bossId, String employeeId) {
        Optional<Employee> boss = employeeRepository.findById(bossId);
        Optional<Employee> employee = employeeRepository.findById(employeeId);

        Employee bossWithInChargeEmployees = new EmployeeBuilder(boss.get())
                .withEmployeesInCharge(Arrays.asList(employee.get()))
                .build();

        employeeRepository.delete(boss.get());
        employeeRepository.save(bossWithInChargeEmployees);
    }
}
