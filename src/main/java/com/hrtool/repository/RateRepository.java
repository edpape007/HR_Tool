package com.hrtool.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hrtool.model.EmployeeRate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RateRepository implements Repository<EmployeeRate> {
    private static final String DATA_FILE_PATH = "./datafileRates.json";

    private Gson gson;

    public RateRepository() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public List<EmployeeRate> findAll() {
        List<EmployeeRate> rateList = new ArrayList<>();

        try {
            String data = new String(Files.readAllBytes(Paths.get(DATA_FILE_PATH)));
            Type listType = new TypeToken<ArrayList<EmployeeRate>>(){}.getType();
            rateList = gson.fromJson(data, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rateList;
    }


    @Override
    public Optional<EmployeeRate> findById(String id) {
        return findAll()
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(EmployeeRate model) {
        List<EmployeeRate> rateList = findAll();
        rateList.add(model);

        saveAll(rateList);
    }

    @Override
    public void saveAll(List<EmployeeRate> models) {
        String data = gson.toJson(models);

        try {
            Files.write(Paths.get(DATA_FILE_PATH), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(EmployeeRate model) {
        List<EmployeeRate> rateList = findAll();

        rateList.remove(model);

        saveAll(rateList);
    }

    @Override
    public void update(EmployeeRate model) {
        List<EmployeeRate> rateList = findAll();

        rateList.remove(model);
        rateList.add(model);

        saveAll(rateList);
    }
}
