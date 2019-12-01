package com.hrtool.controller;

import com.google.gson.Gson;
import com.hrtool.model.AssociateBossRequest;
import com.hrtool.model.Employee;
import com.hrtool.repository.EmployeeRepository;
import com.hrtool.service.BossService;
import com.hrtool.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private Gson gson;
    private EmployeeRepository employeeRepository;
    private BossService bossService;
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, BossService bossService, EmployeeService employeeService) {
        gson = new Gson();
        this.employeeRepository = employeeRepository;
        this.bossService = bossService;
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/listBosses/{id}")
    public String getBossList(@PathVariable ("id") String id) {
        List<Employee> bosses = bossService.findMyPossibleBosses(id);

        return gson.toJson(bosses);
    }

    @PostMapping("/employee/associate")
    public String associateEmployeeToBoss(@RequestBody String data) {
        AssociateBossRequest associateRequest = gson.fromJson(data, AssociateBossRequest.class);
        employeeService.associateBoss(associateRequest.getIdEmployee(), associateRequest.getIdBoss());
        bossService.associateEmployee(associateRequest.getIdBoss(), associateRequest.getIdEmployee());

        return "Models were associated";
    }

    @PutMapping("/employee")
    public String registerEmployee(@RequestBody String employeeData) {
        Employee employee = gson.fromJson(employeeData, Employee.class);


        //TODO do something with employee
        employeeRepository.save(employee);

        return employeeData;
    }

    @DeleteMapping("/employee")
    public String deleteEmployee(@RequestBody String employeeData) {
        Employee employee = gson.fromJson(employeeData, Employee.class);

        employeeRepository.delete(employee);

        return "Deleted " + employee;
    }
}