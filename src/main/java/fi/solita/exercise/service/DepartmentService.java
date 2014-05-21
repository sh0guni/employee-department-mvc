package fi.solita.exercise.service;

import fi.solita.exercise.dao.DepartmentsRepository;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.util.DtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DepartmentService {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private DtoFactory departmentDtoFactory;

    public DepartmentDTO addDepartment(String name) {
        Department department = new Department((name));
        departmentsRepository.save(department);
        return new DepartmentDTO(department.getId(), department.getName());
    }

    public long findDepartmentCount() {
        return departmentsRepository.count();
    }

    public DepartmentDTO getDepartment(long id) {
        Department department = departmentsRepository.getOne(id);
        return departmentDtoFactory.createDepartment(department);
    }

    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
        departmentsRepository.findAll()
                             .forEach(x -> departments.add(departmentDtoFactory
                                     .createDepartment(x)));
        return departments;
    }

    public void deleteDepartment(long id) {
        departmentsRepository.delete(id);
    }
}