package fi.solita.exercise.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "DEPARTMENT")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees = new HashSet<Employee>();

    @SuppressWarnings("UnusedDeclaration")
    protected Department() {
    }

    public long getId() {
        return id;
    }

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}
