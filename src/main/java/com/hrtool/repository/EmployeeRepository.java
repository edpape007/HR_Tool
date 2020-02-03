package com.hrtool.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hrtool.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author edpape
 * Class responsible of employee persistance operations.
 */
@Component
public class EmployeeRepository implements Repository<Employee> {
    private static final String DATA_FILE_PATH = "./datafile.json";
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeRepository.class);

    private Gson gson;

    public EmployeeRepository() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Stores the list of employees in the database.
     * @param employeeList List of employees.
     */
    public void saveAll(List<Employee> employeeList) {
        String data = gson.toJson(employeeList);
        try {
            Files.write(Paths.get(DATA_FILE_PATH), data.getBytes());
        } catch (IOException e) {
            LOG.error("An error occurred when storing employees in the database - {}", e.getMessage());
        }
    }

    /**
     * Adds an employee into the database.
     * @param employee Employee data.
     */
    public void save(Employee employee) {
        List<Employee> employeeList = this.findAll();

        employeeList.add(employee);
        this.saveAll(employeeList);
    }

    /**
     * Gets all the employees from the database.
     * @return List of employees.
     */
    public List<Employee> findAll() {
        List<Employee> employeeList = new ArrayList<>();

        try {
            String data = new String(Files.readAllBytes(Paths.get(DATA_FILE_PATH)));
            Type listType = new TypeToken<ArrayList<Employee>>() {
            }.getType();
            employeeList = gson.fromJson(data, listType);
        } catch (IOException e) {
            LOG.error("An error occurred when trying to get all the employees from the database - {}", e.getMessage());
        }

        return employeeList;
    }

    /**
     * Looks for an employee.
     * @param id id of the employee to look for.
     * @return Employee data or Empty if none is found.
     */
    public Optional<Employee> findById(String id) {
        return findAll()
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    /**
     * Removes an employee from the database.
     * @param employee employee to be removed.
     */
    public void delete(Employee employee) {
        List<Employee> employeeList = findAll();

        employeeList.remove(employee);

        this.saveAll(employeeList);
    }

    /**
     * Updates the data of an employee.
     * @param model employee data to be updated.
     */
    @Override
    public void update(Employee model) {
        List<Employee> employeeList = findAll();

        employeeList.remove(model);
        employeeList.add(model);

        saveAll(employeeList);
    }
}
