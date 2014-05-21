package fi.solita.exercise.util;

import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
import fi.solita.exercise.service.DepartmentDTO;
import fi.solita.exercise.service.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class DtoFactory {

    public EmployeeDTO createEmployee(Employee domain) {
        return new EmployeeDTO(domain.getId(),
                domain.getFirstName(), domain.getLastName(),
                domain.getEmail(), domain.getContractBeginDate(),
                createDepartment(domain.getDepartment()));
    }

    public DepartmentDTO createDepartment(Department domain) {
        return new DepartmentDTO(domain.getId(), domain.getName());
    }
}
