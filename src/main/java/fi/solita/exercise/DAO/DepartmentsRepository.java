package fi.solita.exercise.DAO;

import fi.solita.exercise.domain.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentsRepository extends CrudRepository<Department, Long>{

    public Department findByName(String name);
}
