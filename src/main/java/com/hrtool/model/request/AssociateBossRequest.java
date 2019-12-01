package com.hrtool.model.request;

public class AssociateBossRequest {
    private final String idEmployee;
    private final String idBoss;

    public AssociateBossRequest(String idEmployee, String idBoss) {
        this.idEmployee = idEmployee;
        this.idBoss = idBoss;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public String getIdBoss() {
        return idBoss;
    }
}
