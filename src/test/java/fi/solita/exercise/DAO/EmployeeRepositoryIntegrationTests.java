package fi.solita.exercise.dao;


import static org.junit.Assert.assertEquals;

import javax.validation.ConstraintViolationException;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.solita.exercise.Application;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
import fi.solita.exercise.domain.Municipality;
import fi.solita.exercise.service.TestDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class EmployeeRepositoryIntegrationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    TestDataService testDataService;

    @After
    public void tearDown() {
        testDataService.clearDatabase();
    }

    @Test
    public void addEmployeeTest() throws Exception {
        Department department = testDataService.createDefaultDepartment();
        Municipality municipality = testDataService.createDefaultMunicipality();
        Employee employee = new Employee("Simo", "Solita", "simo@solita.fi", new DateTime(), department, municipality);
        employeeRepository.saveAndFlush(employee);

        Iterable<Employee> employees = employeeRepository.findAll();
        assertEquals(employee.getId(), employees.iterator().next().getId());
        assertEquals(department.getId(), employee.getDepartment().getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addEmployeeWithoutDepartmentTest() throws Exception {
        Municipality municipality = testDataService.createDefaultMunicipality();
        Employee employee = new Employee("Simo", "Solita", "simo@solita.fi", new DateTime(), null, municipality);
        employeeRepository.saveAndFlush(employee);
    }

    @Test(expected = ConstraintViolationException.class)
    public void addEmployeeWithInvalidEmail() throws Exception {
        Department department = testDataService.createDefaultDepartment();
        Municipality municipality = testDataService.createDefaultMunicipality();
        String email = "invalid";
        Employee employee = new Employee("Simo", "Solita", email, new DateTime(), department, municipality);
        employeeRepository.saveAndFlush(employee);
    }
}
