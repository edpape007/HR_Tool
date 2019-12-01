package com.hrtool.model;

import java.util.List;

public class EmployeeBuilder {
    private String id;
    private String name;
    private String jobPosition;
    private List<Employee> employeesInCharge;
    private Employee myBoss;

    public EmployeeBuilder() {}

    public EmployeeBuilder(Employee from) {
        this.id = from.getId();
        this.name = from.getName();
        this.jobPosition = from.getJobPosition();
        this.employeesInCharge = from.getEmployeesInCharge();
        this.myBoss = from.getMyBoss();
    }

    public EmployeeBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public EmployeeBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EmployeeBuilder withJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
        return this;
    }

    public EmployeeBuilder withEmployeesInCharge(List<Employee> employeesInCharge) {
        this.employeesInCharge = employeesInCharge;
        return this;
    }

    public EmployeeBuilder withMyBoss(Employee myBoss) {
        this.myBoss = myBoss;
        return this;
    }

    public Employee build() {
        return new Employee(id, name, jobPosition, employeesInCharge, myBoss);
    }
}
