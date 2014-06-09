package fi.solita.exercise.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@Table(indexes = {
        @Index(columnList = "department")
})
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
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
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

    public Employee(final String firstName, final String lastName, final String email, final DateTime contractBeginDate,
            final Department department, final Municipality municipality) {
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(final Department department) {
        this.department = department;
    }

    public long getDepartmentId() {
        return department.getId();
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(final Municipality municipality) {
        this.municipality = municipality;
    }

    public long getMunicipalityId() {
        return municipality.getId();
    }
}
