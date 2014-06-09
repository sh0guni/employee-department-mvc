package fi.solita.exercise.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.solita.exercise.Application;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class EmployeeServiceIntegrationTests {

    @Autowired
    private EmployeeService service;

    @Autowired
    TestDataService testDataService;

    @Before
    public void setUp() throws Exception {
        testDataService.createDefaultEmployee();
    }

    @After
    public void tearDown() {
        testDataService.clearDatabase();
    }

    @Test
    public void addEmployeeTest() throws Exception {
        Department department = testDataService.createDefaultDepartment();
        EmployeeCreateDTO employee = new EmployeeCreateDTO();
        employee.setFirstName("Simo");
        employee.setLastName("Solita");
        employee.setEmail("simo@solita.fi");
        employee.setContractBeginDate(new DateTime());
        employee.setDepartmentId(department.getId());
        employee.setMunicipalityId(testDataService.createDefaultMunicipality().getId());
        EmployeeDTO newEmployee = service.addEmployee(employee);
        EmployeeDTO employeeFromService = service.getEmployee(newEmployee.getId());
        assertEquals(employee.getFirstName(),
                employeeFromService.getFirstName());
        assertEquals(department.getId(), employeeFromService.getDepartmentId());
    }

    @Test
    public void findEmployeeCountTest() throws Exception {
        assertEquals(1, service.findEmployeeCount());
    }

    @Test
    public void getEmployeesOfDepartmentTest() throws Exception {
        Employee employee = testDataService.createDefaultEmployee();
        List<EmployeeDTO> employees =
                service.getEmployeesOfDepartment(employee.getDepartmentId());
        assertEquals(1, employees.size());
        assertThat(employees, IsIterableContainingInAnyOrder
                .containsInAnyOrder(service.getEmployee(employee.getId())));
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        Employee employee = testDataService.createDefaultEmployee();
        Long departmentId = employee.getDepartmentId();
        service.deleteEmployee(employee.getId());
        assertEquals(1, service.findEmployeeCount());

        List<EmployeeDTO> employees = service.getEmployeesOfDepartment(departmentId);
        assertEquals(0, employees.size());
    }

    @Test
    public void changeEmployeeDepartmentTest() throws Exception {
        EmployeeDTO employee = service.getEmployee(
                testDataService.createDefaultEmployee().getId());
        Long origDepartmentId = employee.getDepartmentId();
        Department newDepartment = testDataService.createDefaultDepartment();
        EmployeeUpdateDTO updatedEmployee = new EmployeeUpdateDTO();
        updatedEmployee.setId(employee.getId());
        updatedEmployee.setFirstName(employee.getFirstName());
        updatedEmployee.setLastName(employee.getLastName());
        updatedEmployee.setEmail(employee.getEmail());
        updatedEmployee.setContractBeginDate(employee.getContractBeginDate());
        updatedEmployee.setDepartmentId(newDepartment.getId());
        updatedEmployee.setMunicipalityId(employee.getMunicipalityId());
        service.updateEmployee(updatedEmployee);

        List<EmployeeDTO> employees = service.getEmployeesOfDepartment(newDepartment.getId());
        assertEquals(1, employees.size());
        assertThat(employees, IsIterableContainingInAnyOrder
                .containsInAnyOrder(service.getEmployee(updatedEmployee.getId())));
        assertEquals(0, service.getEmployeesOfDepartment(origDepartmentId).size());
    }
}