package com.hrtool.service;

import com.google.gson.Gson;
import com.hrtool.model.Employee;
import com.hrtool.model.EmployeeBuilder;
import com.hrtool.model.EmployeeRate;
import com.hrtool.model.Goal;
import com.hrtool.model.request.GoalRequest;
import com.hrtool.model.request.RateRequest;
import com.hrtool.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author edpape
 *
 * This class is responsible for all the possible employee operations.
 */
@Service
public class EmployeeService {
    private Gson gson;
    private Repository<Employee> employeeRepository;
    private Repository<EmployeeRate> rateRepository;

    @Autowired
    public EmployeeService(Repository<Employee> employeeRepository, Repository<EmployeeRate> rateRepository) {
        this.employeeRepository = employeeRepository;
        this.rateRepository = rateRepository;
        this.gson = new Gson();
    }

    /**
     * Create an employee in the database, from a JSON document
     * @param employeeData json document containing the employee data.
     * @return created employee data.
     */
    public Employee createEmployee(String employeeData) {
        Employee employee = gson.fromJson(employeeData, Employee.class);
        employeeRepository.save(employee);

        return employee;
    }

    /**
     * Removes an employee from the database.
     * @param employeeData json document containing the employee data.
     * @return Removed employee.
     */
    public Employee deleteEmployee(String employeeData) {
        Employee employee = gson.fromJson(employeeData, Employee.class);
        employeeRepository.delete(employee);

        return employee;
    }

    /**
     * This operation associates an employee with a boss.
     * @param idEmployee id of the employee that is going to be associated with a boss.
     * @param idBoss id of the boss that is going to be associated with the employee.
     * @return
     */
    public Optional<Employee> associate(String idEmployee, String idBoss) {
        Optional<Employee> employee = employeeRepository.findById(idEmployee);
        Optional<Employee> boss = employeeRepository.findById(idBoss);

        if(employee.isPresent() && boss.isPresent()) {
            Employee employeeWithBoss = new EmployeeBuilder(employee.get())
                    .withMyBoss(boss.get())
                    .build();

            employeeRepository.update(employeeWithBoss);
            return Optional.of(employeeWithBoss);
        }

        return Optional.empty();
    }

    /**
     * Creates a rate for an employee
     * @param rate a rate request containing the id of the employee to be rated, the id of the employee that is doing the rate, and the rate.
     * @return returns the employee rate stored in the database.
     */
    public EmployeeRate rate(RateRequest rate) {
        EmployeeRate employeeRate = new EmployeeRate(UUID.randomUUID().toString(), rate.getEmployeeFromId(), rate.getEmployeeToId(), rate.getRate());
        rateRepository.save(employeeRate);

        return employeeRate;
    }

    /**
     * Creates multiple rates for multiple employees
     * @param rates a list of rates.
     * @return the list of rates stored in the database.
     */
    public List<EmployeeRate> rate(List<RateRequest> rates) {
        return rates.stream()
                .map(this::rate)
                .collect(Collectors.toList());
    }

    /**
     * List all the rates given to an employee
     * @param employeeId Id of the employee to list rates
     * @return List of all the rates
     */
    public List<EmployeeRate> getMyRates(String employeeId) {
        return rateRepository.findAll()
                .stream()
                .filter(rate -> rate.getTo().equals(employeeId))
                .collect(Collectors.toList());
    }

    /**
     * Gets the list of all the employees that can be boss of an employee
     * @param employeeId Id of the employee to list the possible bosses.
     * @return List of bosses.
     */
    public List<Employee> findMyPossibleBosses(String employeeId) {
        return employeeRepository.findAll()
                .stream()
                .filter(item -> !item.getId().equals(employeeId))
                .collect(Collectors.toList());
    }

    /**
     * Set the goals of an employee
     * @param goalRequest Contains the list of goals and the employee that belong to.
     */
    public void setEmployeeGoals(GoalRequest goalRequest) {
        employeeRepository.findById(goalRequest.getEmployeeId())
                .map(employee -> new EmployeeBuilder(employee)
                        .withGoals(goalRequest.getGoals())
                        .build())
                .ifPresent(updatedEmployee -> employeeRepository.update(updatedEmployee));
    }

    /**
     * Looks for the goals of a given employee
     * @param id Id of the employee
     * @return List of goals or an Empty list if none are found.
     */
    public List<Goal> findEmployeeGoals(String id) {
        return employeeRepository
                .findById(id)
                .map(Employee::getGoals)
                .orElseGet(Collections::emptyList);
    }

}
