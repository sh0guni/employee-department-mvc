package fi.solita.exercise.service;

import fi.solita.exercise.dao.DepartmentsRepository;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.util.DtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private DtoFactory departmentDtoFactory;

    @Transactional
    public DepartmentDTO addDepartment(String name) {
        Department department = new Department((name));
        departmentsRepository.save(department);
        return new DepartmentDTO(department.getId(), department.getName(), 0);
    }

    @Transactional(readOnly = true)
    public long findDepartmentCount() {
        return departmentsRepository.count();
    }

    @Transactional(readOnly = true)
    public DepartmentDTO getDepartment(long id) {
        Department department = departmentsRepository.getOne(id);
        return departmentDtoFactory.createDepartment(department);
    }

    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        List<DepartmentDTO> departments = new ArrayList<DepartmentDTO>();
        departmentsRepository.findAll()
                             .forEach(x -> departments.add(departmentDtoFactory
                                     .createDepartment(x)));
        return departments;
    }

    @Transactional
    public DepartmentDTO updateDepartment(DepartmentDTO department) {
        Department domainDepartment = departmentsRepository.getOne(department.getId());
        domainDepartment.setName(department.getName());
        return departmentDtoFactory.createDepartment(domainDepartment);
    }

    @Transactional
    public void deleteDepartment(long id) {
        departmentsRepository.delete(id);
    }
}