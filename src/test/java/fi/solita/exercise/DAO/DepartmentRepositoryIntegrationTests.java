package fi.solita.exercise.dao;


import static org.junit.Assert.assertEquals;

import java.util.List;
import javax.validation.ConstraintViolationException;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.solita.exercise.Application;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.service.TestDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class DepartmentRepositoryIntegrationTests {

    @Autowired
    private DepartmentRepository repository;

    @Autowired
    TestDataService testDataService;

    @After
    public void tearDown() {
        testDataService.clearDatabase();
    }

    @Test
    public void addDepartmentTest() throws Exception {
        Department department = new Department("testDepartment");
        repository.saveAndFlush(department);
        Iterable<Department> departments = repository.findAll();
        assertEquals(department.getName(), departments.iterator().next().getName());
    }

    @Test(expected = ConstraintViolationException.class)
    public void addDepartmentWithEmptyName() {
        repository.saveAndFlush(new Department(""));
    }

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Test
    public void getAllDepartmentsWithEmployeeCount() {
        testDataService.createDefaultDepartment();
        testDataService.createDefaultEmployee();

        List<DepartmentWithEmployeeCountDTO> departmentsWithEmployeeCounts = repository.findAllWithEmployeeCount();

        assertEquals(2, departmentsWithEmployeeCounts.size());
        assertEquals(0, departmentsWithEmployeeCounts.get(0).getEmployeeCount());
        assertEquals(1, departmentsWithEmployeeCounts.get(1).getEmployeeCount());
    }
}
