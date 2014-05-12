package fi.solita.exercise.service;


import fi.solita.exercise.config.JPAConfiguration;
import fi.solita.exercise.domain.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DepartmentRepositoryIntegrationTests {

    @Autowired
    DepartmentsRepository repository;

    @Test
    public void addDepartmentTest() throws Exception {
        repository.save(new Department("depa1"));
    }
}
