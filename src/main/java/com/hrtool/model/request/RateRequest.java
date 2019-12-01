package com.hrtool.model.request;

public class RateRequest {
    private final String employeeFromId;
    private final String employeeToId;
    private final String rate;

    public RateRequest(String employeeFromId, String employeeToId, String rate) {
        this.employeeFromId = employeeFromId;
        this.employeeToId = employeeToId;
        this.rate = rate;
    }

    public String getEmployeeFromId() {
        return employeeFromId;
    }

    public String getEmployeeToId() {
        return employeeToId;
    }

    public String getRate() {
        return rate;
    }
}
