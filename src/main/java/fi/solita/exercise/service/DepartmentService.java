package fi.solita.exercise.service;

import fi.solita.exercise.DAO.DepartmentsRepository;
import fi.solita.exercise.DTO.DepartmentDTO;
import fi.solita.exercise.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DepartmentsRepository departmentsRepository;

    public DepartmentDTO addDepartment(String name) {
        Department department = new Department((name));
        departmentsRepository.save(department);
        return new DepartmentDTO(department);
    }

    public long findDepartmentCount() {
        return departmentsRepository.count();
    }

    public DepartmentDTO getDepartment(String name) {
        Department department = departmentsRepository.findByName(name);
        if (department == null) {
            return null;
        } else {
            return new DepartmentDTO(department);
        }
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
        departmentsRepository.findAll()
                             .forEach(x -> departments.add(new DepartmentDTO(x)));
        return departments;
    }

    public int removeDepartmentByName(String departmentName) {
        return departmentsRepository.deleteByName(departmentName);
    }
}