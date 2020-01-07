package com.hrtool.service;

import com.hrtool.model.Employee;
import com.hrtool.model.EmployeeRate;
import com.hrtool.model.request.RateRequest;
import com.hrtool.repository.EmployeeRepository;
import com.hrtool.repository.MockEmployeeRepository;
import com.hrtool.repository.MockRateRepository;
import com.hrtool.repository.Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class EmployeeServiceTest {

    @Test
    public void shouldDoAssociationTest() {
        MockEmployeeRepository mockEmployeeRepository = new MockEmployeeRepository();

        EmployeeService employeeService = new EmployeeService(mockEmployeeRepository, null);

        Optional<Employee> employee = employeeService.associate("1234", "12345");

        Assert.assertTrue("The employee (1234) with association (12345) exists", employee.isPresent());
        Assert.assertTrue("The boss id (12345) is not associated.", "12345".equals(employee.get().getMyBoss().getId()));
    }

    @Test
    public void shouldNotDoAssociationTest() {
        Repository<Employee> employeeRepository = Mockito.mock(Repository.class);

        Mockito.when(employeeRepository.findById("1234")).thenReturn(Optional.of(new Employee("1234", "Dummy1", "DummyJob", Collections.EMPTY_LIST, null, Collections.EMPTY_LIST)));
        Mockito.when(employeeRepository.findById("123456")).thenReturn(Optional.empty());

        EmployeeService service = new EmployeeService(employeeRepository, null);

        Optional<Employee> employee = service.associate("1234", "123456");

        Assert.assertFalse("Should not exist employee 1234 with association 123456", employee.isPresent());
    }

    @Test
    public void shouldRateEmployee() {
        Repository<EmployeeRate> employeeRateRepository = Mockito.mock(Repository.class);
        EmployeeService service = new EmployeeService(null, employeeRateRepository);

        EmployeeRate rate = service.rate(new RateRequest("Boss1234", "Employee12", "Good job!"));

        Assert.assertNotEquals("The rate doesn't exists", rate, null);
        Assert.assertEquals("The rate was not done from (Boss1234)", "Boss1234", rate.getFrom());
        Assert.assertEquals("The rate was not done to (Employee12)", "Employee12", rate.getTo());
        Assert.assertEquals("The rate description is not as expected", "Good job!", rate.getRate());
    }

    @Test
    public void shouldDoMultipleRatesToEmployee() {
        Repository<EmployeeRate> employeeRateRepository = Mockito.mock(Repository.class);
        EmployeeService service = new EmployeeService(null, employeeRateRepository);

        List<RateRequest> rates = Arrays.asList(
          new RateRequest("Boss1", "Employee1", "Good Job!"),
          new RateRequest("Boss1", "Employee2", "Good Job!"),
          new RateRequest("Boss1", "Employee3", "Bad Job :(")
        );

        List<EmployeeRate> employeeRates = service.rate(rates);

        Assert.assertEquals("Rates are empty!", false, rates.isEmpty());
        Assert.assertEquals("Should have 3 rates", 3, rates.size());

        Assert.assertEquals("The rate was not done from (Boss1)", "Boss1", employeeRates.get(0).getFrom());
        Assert.assertEquals("The rate was not done to (Employee1)", "Employee1", employeeRates.get(0).getTo());
        Assert.assertEquals("The rate description is not as expected", "Good Job!", employeeRates.get(0).getRate());

        Assert.assertEquals("The rate was not done from (Boss1)", "Boss1", employeeRates.get(1).getFrom());
        Assert.assertEquals("The rate was not done to (Employee2)", "Employee2", employeeRates.get(1).getTo());
        Assert.assertEquals("The rate description is not as expected", "Good Job!", employeeRates.get(1).getRate());
    }
}
