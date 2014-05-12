package fi.solita.exercise.service;

import fi.solita.exercise.domain.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentsRepository extends CrudRepository<Department, Long>{
}
