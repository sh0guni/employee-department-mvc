package fi.solita.exercise.service;

import fi.solita.exercise.Application;
import fi.solita.exercise.DAO.DepartmentsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DepartmentServiceIntegrationTest {

    @Autowired
    private DepartmentsRepository repository;

    private DepartmentService service;

    @Before
    public void setUp() {
        service = new DepartmentService(repository);
    }

    @Test
    public void testAddDepartment() {
        String departmentName = "testDepartment1";
        service.addDepartment(departmentName);
        assertEquals(departmentName,
                service.getDepartment("testDepartment1").getName());
    }

    @Test
    public void testFindDepartmentCount() {
        service.addDepartment("dep1");
        service.addDepartment("dep2");
        assertEquals(2, service.findDepartmentCount());
    }

    @Test
    public void testFindDepartmentZeroCount() {
        assertEquals(0, service.findDepartmentCount());
    }

    @Test
    public void testListDepartments() {
        service.addDepartment("dep1");
        service.addDepartment("dep2");

        assertThat(service.getAllDepartments(), containsInAnyOrder("dep1", "dep2"));
    }
}