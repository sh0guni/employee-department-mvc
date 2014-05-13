package fi.solita.exercise.DAO;


import fi.solita.exercise.Application;
import fi.solita.exercise.domain.Department;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DepartmentRepositoryIntegrationTests {

    @Autowired
    private DepartmentsRepository repository;

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void addDepartmentTest() throws Exception {
        Department department1 = new Department("Department1");
        repository.save(department1);
        Iterable<Department> departments = repository.findAll();
        assertEquals(department1.getName(), departments.iterator().next().getName());
    }

    @Test
    public void addDepartmentWithEmptyName() {
        Set<ConstraintViolation<Department>> constraintViolations =
                validator.validate(new Department(""));

        assertEquals(1, constraintViolations.size());
        assertEquals(
                "may not be empty",
                constraintViolations.iterator().next().getMessage()
        );
    }
}
