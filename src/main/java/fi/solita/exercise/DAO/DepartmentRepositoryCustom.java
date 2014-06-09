package fi.solita.exercise.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepositoryCustom {

    List<DepartmentWithEmployeeCountDTO> findAllWithEmployeeCount();
}
