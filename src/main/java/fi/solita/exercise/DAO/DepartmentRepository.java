package fi.solita.exercise.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.solita.exercise.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>, DepartmentRepositoryCustom {

}
