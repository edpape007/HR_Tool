package com.hrtool.model;

import java.util.List;
import java.util.Objects;

public class Employee {
    private final String id;
    private final String name;
    private final String jobPosition;
    private final List<Employee> employeesInCharge;
    private final Employee myBoss;

    public Employee(String id, String name, String jobPosition, List<Employee> employeesInCharge, Employee myBoss) {
        this.id = id;
        this.name = name;
        this.jobPosition = jobPosition;
        this.employeesInCharge = employeesInCharge;
        this.myBoss = myBoss;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public List<Employee> getEmployeesInCharge() {
        return employeesInCharge;
    }

    public Employee getMyBoss() {
        return myBoss;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", jobPosition='" + jobPosition + '\'' +
                ", employeesInCharge=" + employeesInCharge +
                ", myBoss=" + myBoss +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
