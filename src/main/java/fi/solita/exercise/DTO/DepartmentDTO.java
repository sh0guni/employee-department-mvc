package fi.solita.exercise.DTO;

import fi.solita.exercise.domain.Department;

public class DepartmentDTO {

    private String name;

    public DepartmentDTO(Department department) {
        this.name = department.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
