package com.hrtool.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hrtool.model.EmployeeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author edpape
 * Class responsible of rate persistance operations.
 */
@Component
public class RateRepository implements Repository<EmployeeRate> {
    private static final String DATA_FILE_PATH = "./datafileRates.json";
    private static final Logger LOG = LoggerFactory.getLogger(RateRepository.class);

    private Gson gson;

    public RateRepository() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * List of all rates given to all employees.
     * @return List of rates.
     */
    @Override
    public List<EmployeeRate> findAll() {
        List<EmployeeRate> rateList = new ArrayList<>();

        try {
            String data = new String(Files.readAllBytes(Paths.get(DATA_FILE_PATH)));
            Type listType = new TypeToken<ArrayList<EmployeeRate>>(){}.getType();
            rateList = gson.fromJson(data, listType);
        } catch (IOException e) {
            LOG.error("An error occurred trying to get the list of rates - {}", e.getMessage());
        }

        return rateList;
    }

    /**
     * Gets an specific rate.
     * @param id Id of the employee rate
     * @return rate of the employee, or empty if none is found.
     */
    @Override
    public Optional<EmployeeRate> findById(String id) {
        return findAll()
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    /**
     * Stores an employee rate in the database.
     * @param model The employee rate data.
     */
    @Override
    public void save(EmployeeRate model) {
        List<EmployeeRate> rateList = findAll();
        rateList.add(model);

        saveAll(rateList);
    }

    /**
     * Stores all the employee rates in the database.
     * @param models List of employee rates.
     */
    @Override
    public void saveAll(List<EmployeeRate> models) {
        String data = gson.toJson(models);

        try {
            Files.write(Paths.get(DATA_FILE_PATH), data.getBytes());
        } catch (IOException e) {
            LOG.error("An error occurred trying to save employee rates - {}", e.getMessage());
        }
    }

    /**
     * Removes an employee rate from the database.
     * @param model Employee rate to be removed.
     */
    @Override
    public void delete(EmployeeRate model) {
        List<EmployeeRate> rateList = findAll();

        rateList.remove(model);

        saveAll(rateList);
    }

    /**
     * Updates an employee rate.
     * @param model Updated version of the employee rate.
     */
    @Override
    public void update(EmployeeRate model) {
        List<EmployeeRate> rateList = findAll();

        rateList.remove(model);
        rateList.add(model);

        saveAll(rateList);
    }
}
