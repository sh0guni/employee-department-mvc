package fi.solita.exercise.dao;


import fi.solita.exercise.Application;
import fi.solita.exercise.domain.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class DepartmentRepositoryIntegrationTests {

    @Autowired
    private DepartmentsRepository repository;

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
}
