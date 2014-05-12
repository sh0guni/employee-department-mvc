package fi.solita.exercise.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private long employeeIdentifier;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private Municipality municipality;

    private String email;

    private Date contractBeginDate;

    @Column(nullable = false)
    private Department department;

    protected Employee() {
    }

    public Employee(long employeeIdentifier, String firstName, String lastName, Department department) {
        this.id = id;
        this.employeeIdentifier = employeeIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public long getEmployeeIdentifier() {
        return employeeIdentifier;
    }

    public void setEmployeeIdentifier(long employeeIdentifier) {
        this.employeeIdentifier = employeeIdentifier;
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

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getContractBeginDate() {
        return contractBeginDate;
    }

    public void setContractBeginDate(Date contractBeginDate) {
        this.contractBeginDate = contractBeginDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
