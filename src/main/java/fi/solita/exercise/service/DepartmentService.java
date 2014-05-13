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

    public Iterable<String> getAllDepartments() {
        Collection<String> departments = new ArrayList<String>();
        for (Department dep : departmentsRepository.findAll()) {
            departments.add(dep.getName());
        }
        return departments;
    }
}