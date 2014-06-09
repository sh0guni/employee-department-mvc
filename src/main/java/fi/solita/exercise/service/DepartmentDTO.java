package fi.solita.exercise.service;

public class DepartmentDTO {

    private long id;

    private String name;

    private int employeeCount;

    public DepartmentDTO(final long id, final String name, final int employeeCount) {
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

    public void setName(final String name) {
        this.name = name;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(final int employeeCount) {
        this.employeeCount = employeeCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        DepartmentDTO that = (DepartmentDTO) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
