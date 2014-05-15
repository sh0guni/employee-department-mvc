package fi.solita.exercise.service;

import fi.solita.exercise.dao.DepartmentsRepository;
import fi.solita.exercise.dao.EmployeeRepository;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
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
    private DepartmentsRepository departmentsRepository;

    public EmployeeDTO addEmployee(String firstName, String lastName,
                                   String email, Date contractBeginDate,
                                   long departmentId) {
        Department department = departmentsRepository.getOne(departmentId);
        Employee employee = new Employee(firstName, lastName, email,
                contractBeginDate, department);
        employeeRepository.save(employee);
        return new EmployeeDTO(employee.getId(), employee.getFirstName(), employee.getLastName());
    }

    public long findEmployeeCount() {
        return employeeRepository.count();
    }

    public EmployeeDTO getEmployee(long id) {
        Employee employee = employeeRepository.getOne(id);
        return new EmployeeDTO(employee.getId(), employee.getFirstName(), employee.getLastName());
    }

    public List<EmployeeDTO> getAllDepartments() {
        List<EmployeeDTO> employees = new ArrayList<EmployeeDTO>();
        employeeRepository.findAll()
                             .forEach(x -> employees.add(new EmployeeDTO(x.getId(),
                                     x.getFirstName(), x.getLastName())));
        return employees;
    }

    public void deleteEmployee(long id) {
        employeeRepository.delete(id);
    }
}