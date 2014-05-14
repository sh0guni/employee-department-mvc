package fi.solita.exercise.DAO;

import fi.solita.exercise.domain.Department;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentsRepository extends CrudRepository<Department, Long>{

    public Department findByName(String name);

    @Modifying
    @Query("delete from DEPARTMENTS where name = ?1")
    public int deleteByName(String name);
}
