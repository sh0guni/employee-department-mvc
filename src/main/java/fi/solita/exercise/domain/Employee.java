package fi.solita.exercise.domain;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NotEmpty
    private String firstName;

    @Column
    @NotEmpty
    private String lastName;

    @Column
    @NotEmpty
    @Email
    private String email;

    @Column
    @NotNull
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime contractBeginDate;

    @ManyToOne
    @JoinColumn(name = "department", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "municipality", nullable = false)
    private Municipality municipality;

    @SuppressWarnings("UnusedDeclaration")
    protected Employee() {
    }

    public Employee(String firstName, String lastName, String email, DateTime contractBeginDate, Department department, Municipality municipality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contractBeginDate = contractBeginDate;
        this.department = department;
        this.municipality = municipality;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public long getDepartmentId() {
        return department.getId();
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public long getMunicipalityId() {
        return municipality.getId();
    }
}
