package fi.solita.exercise.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
import fi.solita.exercise.domain.Municipality;

@Service
@Transactional
public class TestDataService {

    @PersistenceContext
    EntityManager entityManager;

    @Modifying
    public Department createDefaultDepartment() {
        Department department = new Department("dep1");

        entityManager.persist(department);
        return department;
    }

    @Modifying
    public Municipality createDefaultMunicipality() {
        Municipality municipality = new Municipality("mun1");

        entityManager.persist(municipality);
        return municipality;
    }

    @Modifying
    public Employee createDefaultEmployee() {
        Employee employee = new Employee("Simo", "Solita", "simo@solita.fi", new DateTime(),
                createDefaultDepartment(), createDefaultMunicipality());

        entityManager.persist(employee);
        return employee;
    }

    public void clearDatabase() {
        entityManager.createNativeQuery("delete from employee").executeUpdate();
        entityManager.createNativeQuery("delete from municipality").executeUpdate();
        entityManager.createNativeQuery("delete from department").executeUpdate();
    }
}
