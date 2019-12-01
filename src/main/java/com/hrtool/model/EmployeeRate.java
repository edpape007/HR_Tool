package com.hrtool.model;

import java.util.Objects;

public class EmployeeRate {
    private final String id;
    private final String from;
    private final String to;
    private final String rate;

    public EmployeeRate(String id, String from, String to, String rate) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getRate() {
        return rate;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRate that = (EmployeeRate) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
