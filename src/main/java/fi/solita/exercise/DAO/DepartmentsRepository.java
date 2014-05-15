package fi.solita.exercise.dao;

import fi.solita.exercise.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentsRepository extends JpaRepository<Department, Long> {

//    public Department findByName(String name);
//
//    @Modifying
//    @Query("delete from DEPARTMENT where name = ?1")
//    public int deleteByName(String name);
}
