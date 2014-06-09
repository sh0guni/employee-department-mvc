package fi.solita.exercise.dao;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;

@SuppressWarnings("InstanceVariableMayNotBeInitialized")
@SqlResultSetMapping(name = "EmployeeCountResults",
        classes = {
                @ConstructorResult(
                        targetClass = DepartmentWithEmployeeCountDTO.class,
                        columns = {
                            @ColumnResult(name = "id"),
                            @ColumnResult(name = "name"),
                            @ColumnResult(name = "employeeCount", type = Integer.class)
                        }
                )
        }
)
public class DepartmentWithEmployeeCountDTO {
    private long id;

    private String name;

    private long employeeCount;

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(final long employeeCount) {
        this.employeeCount = employeeCount;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DepartmentWithEmployeeCountDTO that = (DepartmentWithEmployeeCountDTO) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
