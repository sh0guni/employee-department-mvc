package fi.solita.exercise.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    private String email;

    private Date contractBeginDate;

    @ManyToOne
    @JoinColumn(name = "departmentid")
    private Department department;

    protected Employee() {
    }

    public Employee(String firstName, String lastName, String email, Date contractBeginDate, Department department) {
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
