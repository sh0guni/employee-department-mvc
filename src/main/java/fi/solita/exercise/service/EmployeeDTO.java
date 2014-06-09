package fi.solita.exercise.service;

import org.joda.time.DateTime;

public class EmployeeDTO {

    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private DateTime contractBeginDate;

    private long departmentId;

    private long municipalityId;

    private EmployeeDTO() {
    }

    public EmployeeDTO(final long id, final String firstName, final String lastName, final String email, final DateTime contractBeginDate, final long departmentId, final long municipalityId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contractBeginDate = contractBeginDate;
        this.departmentId = departmentId;
        this.municipalityId = municipalityId;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public DateTime getContractBeginDate() {
        return contractBeginDate;
    }

    public void setContractBeginDate(final DateTime contractBeginDate) {
        this.contractBeginDate = contractBeginDate;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(final long departmentId) {
        this.departmentId = departmentId;
    }

    public long getMunicipalityId() {
        return municipalityId;
    }

    public void setMunicipalityId(final long municipalityId) {
        this.municipalityId = municipalityId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO that = (EmployeeDTO) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
