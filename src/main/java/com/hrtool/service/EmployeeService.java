package com.hrtool.service;

import com.hrtool.model.Employee;
import com.hrtool.model.EmployeeBuilder;
import com.hrtool.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void associateBoss(String idEmployee, String idBoss) {
        Optional<Employee> employee = employeeRepository.findById(idEmployee);
        Optional<Employee> boss = employeeRepository.findById(idBoss);

        Employee employeeWithBoss = new EmployeeBuilder(employee.get())
                .withMyBoss(boss.get())
                .build();

        employeeRepository.delete(employee.get());
        employeeRepository.save(employeeWithBoss);
    }
}
