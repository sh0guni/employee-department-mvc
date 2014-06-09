package fi.solita.exercise.service;

import org.joda.time.DateTime;

public class EmployeeCreateDTO {

    private String firstName;

    private String lastName;

    private String email;

    private DateTime contractBeginDate;

    private long departmentId;

    private long municipalityId;

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
}
