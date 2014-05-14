package fi.solita.exercise.service;

import fi.solita.exercise.DAO.DepartmentsRepository;
import fi.solita.exercise.DTO.DepartmentDTO;
import fi.solita.exercise.domain.Department;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class DepartmentService {

    private final DepartmentsRepository departmentsRepository;

    public DepartmentService(DepartmentsRepository repository) {
        this.departmentsRepository = repository;
    }

    public void addDepartment(String name) {
        departmentsRepository.save(new Department(name));
    }

    public long findDepartmentCount() {
        return departmentsRepository.count();
    }

    public DepartmentDTO getDepartment(String name) {
        return new DepartmentDTO(departmentsRepository.findByName(name));
    }

    public Collection<DepartmentDTO> getAllDepartments() {
        Collection<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
        departmentsRepository.findAll()
                             .forEach(x -> departments.add(new DepartmentDTO(x)));
        return departments;
    }

    public void removeDepartmentByName(String departmentName) {
        departmentsRepository.deleteByName(departmentName);
    }
}