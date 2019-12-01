package com.hrtool.model;

public class EmployeeRate {
    private final Employee from;
    private final Employee to;
    private final String rate;

    public EmployeeRate(Employee from, Employee to, String rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public Employee getFrom() {
        return from;
    }

    public Employee getTo() {
        return to;
    }

    public String getRate() {
        return rate;
    }
}
