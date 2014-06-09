package fi.solita.exercise.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;

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
public class DepartmentServiceIntegrationTests {

    @Autowired
    private DepartmentService service;

    @Autowired
    TestDataService testDataService;

    @Before
    public void setUp() {
        testDataService.createDefaultDepartment();
    }

    @After
    public void tearDown() {
        testDataService.clearDatabase();
    }

    @Test
    public void addDepartmentTest() {
        DepartmentDTO department = service.addDepartment("testDepartment1");
        assertEquals(department.getName(),
                service.getDepartment(department.getId()).getName());
    }

    @Test
    public void findDepartmentCountTest() {

        assertEquals(1, service.findDepartmentCount());
    }

    @Test
    public void getAllDepartmentsTest() {
        assertThat(
                service.getAllDepartments().stream()
                        .map(x -> x.getName()).collect(Collectors.toList()),
                containsInAnyOrder("dep1")
        );
    }

    @Test
    public void deleteDepartmentTest() {
        Department department = testDataService.createDefaultDepartment();
        service.deleteDepartment(department.getId());
        assertEquals(1, service.findDepartmentCount());
        assertThat(
                service.getAllDepartments().stream()
                        .map(x -> x.getName()).collect(Collectors.toList()),
                containsInAnyOrder("dep1")
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteDepartmentWithEmployeesTest() {
        Employee employee = testDataService.createDefaultEmployee();
        service.deleteDepartment(employee.getDepartmentId());
    }
}