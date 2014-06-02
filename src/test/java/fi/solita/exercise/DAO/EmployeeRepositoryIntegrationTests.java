package fi.solita.exercise.dao;


import fi.solita.exercise.Application;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
import fi.solita.exercise.domain.Municipality;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class EmployeeRepositoryIntegrationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Test
    public void addEmployeeTest() throws Exception {
        Department department = new Department("testDepartment");
        departmentsRepository.saveAndFlush(department);
        Municipality municipality = municipalityRepository.findOne(Long.valueOf(1));
        Employee employee = new Employee("Simo", "Solita", "simo@solita.fi", new DateTime(), department, municipality);
        employeeRepository.saveAndFlush(employee);
        department.addEmployee(employee);


        Iterable<Employee> employees = employeeRepository.findAll();
        assertEquals(employee.getId(), employees.iterator().next().getId());
        assertEquals(department.getId(), employee.getDepartment().getId());
        assertEquals(employee.getId(), department.getEmployees().iterator().next().getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addEmployeeWithoutDepartmentTest() throws Exception {
        Municipality municipality = municipalityRepository.findOne(Long.valueOf(1));
        Employee employee = new Employee("Simo", "Solita", "simo@solita.fi", new DateTime(), null, municipality);
        employeeRepository.saveAndFlush(employee);
    }

    @Test(expected = ConstraintViolationException.class)
    public void addEmployeeWithInvalidEmail() throws Exception {
        Department department = new Department("testDepartment");
        departmentsRepository.saveAndFlush(department);
        Municipality municipality = municipalityRepository.findOne(Long.valueOf(1));
        Employee employee = new Employee("Simo", "Solita", "invalid", new DateTime(), department, municipality);
        employeeRepository.saveAndFlush(employee);
    }
}
