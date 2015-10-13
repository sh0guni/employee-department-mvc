package fi.solita.exercise.util;

import fi.solita.exercise.domain.Department;
import fi.solita.exercise.domain.Employee;
import fi.solita.exercise.domain.Municipality;
import fi.solita.exercise.service.DepartmentDTO;
import fi.solita.exercise.service.EmployeeDTO;
import fi.solita.exercise.service.MunicipalityDTO;
import org.springframework.stereotype.Component;

@Component
public class DtoFactory {

    public EmployeeDTO createEmployee(final Employee domain) {
        return new EmployeeDTO(domain.getId(),
                domain.getFirstName(), domain.getLastName(),
                domain.getEmail(), domain.getContractBeginDate(),
                domain.getDepartmentId(), domain.getMunicipalityId());
    }

    public DepartmentDTO createDepartment(final Department domain) {
        return new DepartmentDTO(domain.getId(), domain.getName(), domain.getEmployeeCount());
    }

    public MunicipalityDTO createMunicipality(final Municipality domain) {
        return new MunicipalityDTO(domain.getId(), domain.getName());
    }
}
