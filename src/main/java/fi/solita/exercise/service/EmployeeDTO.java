package fi.solita.exercise.service;

import org.joda.time.DateTime;

public class EmployeeDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private DateTime contractBeginDate;

    private DepartmentDTO department;

    private long departmentId;

    private EmployeeDTO() {
    }

    public EmployeeDTO(long id, String firstName, String lastName, String email, DateTime contractBeginDate, DepartmentDTO department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contractBeginDate = contractBeginDate;
        this.department = department;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DateTime getContractBeginDate() {
        return contractBeginDate;
    }

    public void setContractBeginDate(DateTime contractBeginDate) {
        this.contractBeginDate = contractBeginDate;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeDTO that = (EmployeeDTO) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
