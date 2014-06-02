package fi.solita.exercise.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import fi.solita.exercise.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DatabaseSetup(value="/serviceTestData.xml", type= DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(value="/serviceTestData.xml", type= DatabaseOperation.DELETE_ALL)
public class DepartmentServiceIntegrationTests {

    @Autowired
    private DepartmentService service;

    @Test
    public void addDepartmentTest() {
        DepartmentDTO department = service.addDepartment("testDepartment1");
        assertEquals(department.getName(),
                service.getDepartment(department.getId()).getName());
    }

    @Test
    public void findDepartmentCountTest() {
        assertEquals(2, service.findDepartmentCount());
    }

    @Test
    public void getAllDepartmentsTest() {
        assertThat(
                service.getAllDepartments().stream()
                        .map(x -> x.getName()).collect(Collectors.toList()),
                containsInAnyOrder("dep1", "dep2")
        );
    }

    @Test
    public void deleteDepartmentTest() {
        service.deleteDepartment(200);
        assertEquals(1, service.findDepartmentCount());
        assertThat(
                service.getAllDepartments().stream()
                        .map(x -> x.getName()).collect(Collectors.toList()),
                containsInAnyOrder("dep1")
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteDepartmentWithEmployeesTest() {
        service.deleteDepartment(100);
    }
}