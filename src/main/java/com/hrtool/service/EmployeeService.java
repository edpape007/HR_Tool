package com.hrtool.service;

import com.hrtool.model.Employee;
import com.hrtool.model.EmployeeBuilder;
import com.hrtool.model.EmployeeRate;
import com.hrtool.model.Goal;
import com.hrtool.model.request.GoalRequest;
import com.hrtool.model.request.RateRequest;
import com.hrtool.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private Repository<Employee> employeeRepository;
    private Repository<EmployeeRate> rateRepository;

    @Autowired
    public EmployeeService(Repository<Employee> employeeRepository, Repository<EmployeeRate> rateRepository) {
        this.employeeRepository = employeeRepository;
        this.rateRepository = rateRepository;
    }

    public Employee associate(String idEmployee, String idBoss) {
        Optional<Employee> employee = employeeRepository.findById(idEmployee);
        Optional<Employee> boss = employeeRepository.findById(idBoss);

        Employee employeeWithBoss = new EmployeeBuilder(employee.get())
                .withMyBoss(boss.get())
                .build();

        employeeRepository.delete(employee.get());
        employeeRepository.save(employeeWithBoss);

        return employeeWithBoss;
    }

    public void rate(RateRequest rate) {
        rateRepository.save(new EmployeeRate(UUID.randomUUID().toString(), rate.getEmployeeFromId(), rate.getEmployeeToId(), rate.getRate()));
    }

    public void rate(List<RateRequest> rates) {
        rates.stream()
                .forEach(this::rate);
    }

    public List<EmployeeRate> getMyRates(String employeeId) {
        return rateRepository.findAll()
                .stream()
                .filter(rate -> rate.getTo().equals(employeeId))
                .collect(Collectors.toList());
    }

    public List<Employee> findMyPossibleBosses(String employeeId) {
        return employeeRepository.findAll()
                .stream()
                .filter(item -> !item.getId().equals(employeeId))
                .collect(Collectors.toList());
    }

    public void setEmployeeGoals(GoalRequest goalRequest) {
        Employee employee = employeeRepository.findById((goalRequest.getEmployeeId())).get();
        Employee updatedEmployee = new EmployeeBuilder(employee).withGoals(goalRequest.getGoals()).build();
        employeeRepository.delete(employee);
        employeeRepository.save(updatedEmployee);
    }


    public List<Goal> findEmployeeGoals(String id) {
        Employee employee = employeeRepository.findById(id).get();
        return employee.getGoals();
    }

}