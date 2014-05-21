package fi.solita.exercise.dao;


import fi.solita.exercise.Application;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EmployeeRepositoryIntegrationTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Test
    public void addEmployeeTest() throws Exception {
        Department department = new Department("testDepartment");
        departmentsRepository.saveAndFlush(department);
        Employee employee = new Employee("Simo", "Solita", "simo@solita.fi", new Date(), department);
        department.addEmployee(employee);
        employeeRepository.saveAndFlush(employee);
        Iterable<Employee> employees = employeeRepository.findAll();
        assertEquals(employee.getId(), employees.iterator().next().getId());
        assertEquals(department.getId(), employee.getDepartment().getId());
        assertEquals(
                employee.getId(),
                department.getEmployees().iterator().next().getId());
    }
}
