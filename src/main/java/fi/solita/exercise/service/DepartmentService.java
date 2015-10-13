package fi.solita.exercise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.solita.exercise.dao.DepartmentRepository;
import fi.solita.exercise.dao.DepartmentWithEmployeeCountDTO;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.util.DtoFactory;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DtoFactory departmentDtoFactory;

    @Transactional
    public DepartmentDTO addDepartment(final String name) {
        Department department = new Department((name));
        departmentRepository.save(department);
        return departmentDtoFactory.createDepartment(department);
    }

    @Transactional(readOnly = true)
    public long findDepartmentCount() {
        return departmentRepository.count();
    }

    @Transactional(readOnly = true)
    public DepartmentDTO getDepartment(final long id) {
        Department department = departmentRepository.getOne(id);
        return departmentDtoFactory.createDepartment(department);
    }

    @Transactional(readOnly = true)
    public List<DepartmentWithEmployeeCountDTO> getAllDepartments() {
        return departmentRepository.findAllWithEmployeeCount();
    }

    @Transactional
    public DepartmentDTO updateDepartment(final DepartmentUpdateDTO department) {
        Department domainDepartment = departmentRepository.getOne(department.getId());
        domainDepartment.setName(department.getName());
        return departmentDtoFactory.createDepartment(domainDepartment);
    }

    @Transactional
    public void deleteDepartment(final long id) {
        Department department = departmentRepository.getOne(id);
        if (department.getEmployeeCount() > 0) {
            throw new IllegalArgumentException("Cannot remove department with employees");
        } else {
            departmentRepository.delete(department);
        }
    }
}
