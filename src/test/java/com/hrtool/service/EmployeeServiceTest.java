package com.hrtool.service;

import com.hrtool.model.Employee;
import com.hrtool.repository.EmployeeRepository;
import com.hrtool.repository.MockEmployeeRepository;
import com.hrtool.repository.MockRateRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @Test
    public void doAssociationTest() {
        MockEmployeeRepository mockEmployeeRepository = new MockEmployeeRepository();
        MockRateRepository mockRateRepository = new MockRateRepository();

        EmployeeService employeeService = new EmployeeService(mockEmployeeRepository, mockRateRepository);

        Employee employee = employeeService.associate("1234", "12345");

        Assert.assertTrue("The boss id (12345) is not associated.", "12345".equals(employee.getMyBoss().getId()));
    }
}
