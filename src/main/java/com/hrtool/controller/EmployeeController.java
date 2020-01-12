package com.hrtool.controller;

import com.google.gson.Gson;
import com.hrtool.model.Goal;
import com.hrtool.model.request.AssociateBossRequest;
import com.hrtool.model.Employee;
import com.hrtool.model.request.GoalRequest;
import com.hrtool.repository.EmployeeRepository;
import com.hrtool.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    private Gson gson;
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        gson = new Gson();
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    @GetMapping("/employee/listBosses/{id}")
    public String getBossList(@PathVariable ("id") String id) {
        List<Employee> bosses = employeeService.findMyPossibleBosses(id);

        return gson.toJson(bosses);
    }

    @PostMapping("/employee/associate")
    public String associateEmployees(@RequestBody String data) {
        AssociateBossRequest associateRequest = gson.fromJson(data, AssociateBossRequest.class);
        employeeService.associate(associateRequest.getIdEmployee(), associateRequest.getIdBoss());
        employeeService.associate(associateRequest.getIdBoss(), associateRequest.getIdEmployee());

        return "Models were associated";
    }

    @PutMapping("/employee")
    public String registerEmployee(@RequestBody String employeeData) {
        Employee employee = gson.fromJson(employeeData, Employee.class);

        employeeRepository.save(employee);

        return employeeData;
    }

    @PostMapping("/employee/goals")
    public void setGoals(@RequestBody String goalsData){
        GoalRequest goalRequest = gson.fromJson(goalsData, GoalRequest.class);
        employeeService.setEmployeeGoals(goalRequest);
    }

    @GetMapping("/employee/goals/{id}")
    public String getGoalsList(@PathVariable ("id") String id) {
        List<Goal> goals = employeeService.findEmployeeGoals(id);
        return gson.toJson(goals);
    }


    @DeleteMapping("/employee")
    public String deleteEmployee(@RequestBody String employeeData) {
        Employee employee = gson.fromJson(employeeData, Employee.class);

        employeeRepository.delete(employee);

        return "Deleted " + employee;
    }
}