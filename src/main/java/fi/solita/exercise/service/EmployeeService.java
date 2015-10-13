package fi.solita.exercise.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.solita.exercise.dao.DepartmentRepository;
import fi.solita.exercise.dao.EmployeeRepository;
import fi.solita.exercise.dao.MunicipalityRepository;
import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
import fi.solita.exercise.domain.Municipality;
import fi.solita.exercise.util.DtoFactory;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private DtoFactory employeeDtoFactory;

    @Transactional
    public EmployeeDTO addEmployee(final EmployeeCreateDTO employee) {
        Department department = departmentRepository.getOne(employee.getDepartmentId());
        Municipality municipality = municipalityRepository.getOne(employee.getMunicipalityId());
        Employee newEmployee = new Employee(employee.getFirstName(), employee.getLastName(),
                employee.getEmail(), employee.getContractBeginDate(), department, municipality);
        department.addEmployee(newEmployee);
        employeeRepository.save(newEmployee);
        return employeeDtoFactory.createEmployee(newEmployee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(final EmployeeUpdateDTO employee) {
        Employee domainEmployee = employeeRepository.getOne(employee.getId());
        domainEmployee.setFirstName(employee.getFirstName());
        domainEmployee.setLastName(employee.getLastName());
        domainEmployee.setEmail(employee.getEmail());
        domainEmployee.setContractBeginDate(employee.getContractBeginDate());

        if (domainEmployee.getDepartmentId() != employee.getDepartmentId()) {
            Department department = domainEmployee.getDepartment();
            department.removeEmployee(domainEmployee);
            Department newDepartment = departmentRepository.getOne(employee.getDepartmentId());
            domainEmployee.setDepartment(newDepartment);
            newDepartment.addEmployee(domainEmployee);
        }

        if (domainEmployee.getMunicipalityId() != employee.getMunicipalityId()) {
            domainEmployee.setMunicipality(
                    municipalityRepository
                            .getOne(employee.getMunicipalityId()));
        }

        return employeeDtoFactory.createEmployee(domainEmployee);
    }

    @Transactional(readOnly = true)
    public long findEmployeeCount() {
        return employeeRepository.count();
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployee(final long id) {
        Employee employee = employeeRepository.getOne(id);
        return employeeDtoFactory.createEmployee(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesOfDepartment(final long departmentId) {
        Department department = departmentRepository.getOne(departmentId);
        List<EmployeeDTO> employees = new ArrayList<>();
        department.getEmployees().forEach(x -> employees.add(
                                     employeeDtoFactory.createEmployee(x)));
        return employees;
    }

    @Transactional
    public void deleteEmployee(final long id) {
        Employee employee = employeeRepository.getOne(id);
        Department department = employee.getDepartment();
        department.removeEmployee(employee);
        employeeRepository.delete(employee);
    }
}
