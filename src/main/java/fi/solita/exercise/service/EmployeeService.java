package fi.solita.exercise.service;

import fi.solita.exercise.dao.DepartmentsRepository;
import fi.solita.exercise.dao.EmployeeRepository;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
import fi.solita.exercise.util.DtoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private DtoFactory employeeDtoFactory;

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

    public long findEmployeeCount() {
        return employeeRepository.count();
    }

    public EmployeeDTO getEmployee(long id) {
        Employee employee = employeeRepository.getOne(id);
        return employeeDtoFactory.createEmployee(employee);
    }

    public List<EmployeeDTO> getEmployeesOfDepartment(long departmentId) {
        Department department = departmentsRepository.getOne(departmentId);
        List<EmployeeDTO> employees = new ArrayList<>();
        department.getEmployees().forEach(x -> employees.add(
                                     employeeDtoFactory.createEmployee(x)));
        return employees;
    }

    public void deleteEmployee(long id) {
        employeeRepository.delete(id);
    }
}