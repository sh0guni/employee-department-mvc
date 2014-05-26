package fi.solita.exercise.service;

import fi.solita.exercise.dao.DepartmentsRepository;
import fi.solita.exercise.dao.EmployeeRepository;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
import fi.solita.exercise.util.DtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private DtoFactory employeeDtoFactory;

    @Transactional
    public EmployeeDTO addEmployee(String firstName, String lastName,
                                   String email, Date contractBeginDate,
                                   long departmentId) {
        Department department = departmentsRepository.getOne(departmentId);
        Employee employee = new Employee(firstName, lastName, email,
                contractBeginDate, department);
        department.addEmployee(employee);
        employeeRepository.save(employee);
        return employeeDtoFactory.createEmployee(employee);
    }

    @Transactional(readOnly = true)
    public long findEmployeeCount() {
        return employeeRepository.count();
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployee(long id) {
        Employee employee = employeeRepository.getOne(id);
        return employeeDtoFactory.createEmployee(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesOfDepartment(long departmentId) {
        Department department = departmentsRepository.getOne(departmentId);
        List<EmployeeDTO> employees = new ArrayList<>();
        department.getEmployees().forEach(x -> employees.add(
                                     employeeDtoFactory.createEmployee(x)));
        return employees;
    }

    @Transactional
    public void deleteEmployee(long id) {
        employeeRepository.delete(id);
    }
}