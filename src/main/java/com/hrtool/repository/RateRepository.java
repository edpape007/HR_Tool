package com.hrtool.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hrtool.model.Employee;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RateRepository {
    private static final String DATA_FILE_PATH = "./datafileRates.json";

    private Gson gson;

    public RateRepository() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    private void saveAll(List<Employee> employeeList) {
        String data = gson.toJson(employeeList);
        try {
            Files.write(Paths.get(DATA_FILE_PATH), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(Employee employee) {
        try {
            List<Employee> employeeList = this.findAll();
            employeeList.add(employee);
            String data = gson.toJson(employeeList);

            Files.write(Paths.get(DATA_FILE_PATH), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> findAll() {
        List<Employee> employeeList = new ArrayList<>();

        try {
            String data = new String(Files.readAllBytes(Paths.get(DATA_FILE_PATH)));
            Type listType = new TypeToken<ArrayList<Employee>>(){}.getType();
            employeeList = gson.fromJson(data, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    public Optional<Employee> findById(String id) {
        return findAll()
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    public void delete(Employee employee) {
        List<Employee> employeeList = findAll();

        employeeList.remove(employee);

        this.saveAll(employeeList);
    }
}
