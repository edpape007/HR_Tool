package com.hrtool.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hrtool.model.request.RateRequest;
import com.hrtool.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RateController {

    private final Gson gson;
    private final EmployeeService employeeService;

    public RateController(EmployeeService employeeService) {
        this.gson = new Gson();
        this.employeeService = employeeService;
    }

    @PutMapping("rate/boss")
    public String rateBoss(@RequestBody String data) {
        RateRequest rateRequest = gson.fromJson(data, RateRequest.class);

        employeeService.rate(rateRequest);

        return "Rated!";
    }

    @PutMapping("rate/employees")
    public String rateEmployees(@RequestBody String data) {
        Type listType = new TypeToken<ArrayList<RateRequest>>(){}.getType();
        List<RateRequest> rates = gson.fromJson(data, listType);

        employeeService.rate(rates);

        return "All rated!";
    }

    @GetMapping("rate/{id}")
    public String getMyRates(@PathVariable ("id") String id) {
        return gson.toJson(employeeService.getMyRates(id));
    }
}
