package fi.solita.exercise.service;

public class DepartmentDTO {

    private long id;

    private String name;

    private int employeeCount;

    private DepartmentDTO() {
    }

    public DepartmentDTO(long id, String name, int employeeCount) {
        this.id = id;
        this.name = name;
        this.employeeCount = employeeCount;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentDTO that = (DepartmentDTO) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
