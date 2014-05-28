package fi.solita.exercise.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import fi.solita.exercise.Application;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup("/employeeServiceTestData.xml")
public class EmployeeServiceIntegrationTests {

    @Autowired
    private EmployeeService service;

    @Test
    public void getEmployeeTest() throws Exception {
        EmployeeDTO employee = service.getEmployee(100);
        assertEquals(100, employee.getId());
        assertEquals("fname1", employee.getFirstName());
        assertEquals(100, employee.getDepartment().getId());
        assertEquals("dep1", employee.getDepartment().getName());
    }

    @Test
    public void addEmployeeTest() throws Exception {
        EmployeeDTO employee = service.addEmployee(new EmployeeDTO(0, "Simo", "Solita",
                "simo@solita.fi", new DateTime(), new DepartmentDTO(100, "dep1", 0)));
        EmployeeDTO employeeFromService = service.getEmployee(employee.getId());
        assertEquals(employee.getFirstName(),
                employeeFromService.getFirstName());
        assertEquals("dep1", employeeFromService.getDepartment().getName());
    }

    @Test
    public void findEmployeeCountTest() throws Exception {
        assertEquals(2, service.findEmployeeCount());
    }

    @Test
    public void getEmployeesOfDepartmentTest() throws Exception {
        List<EmployeeDTO> employees = service.getEmployeesOfDepartment(100);
        assertEquals(2, employees.size());
        assertThat(employees, IsIterableContainingInAnyOrder
                .containsInAnyOrder(service.getEmployee(100),
                        service.getEmployee(200)));
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        service.deleteEmployee(100);
        assertEquals(1, service.findEmployeeCount());

        List<EmployeeDTO> employees = service.getEmployeesOfDepartment(100);
        assertEquals(1, employees.size());
        assertThat(employees, IsIterableContainingInAnyOrder
                .containsInAnyOrder(service.getEmployee(200)));
    }
}