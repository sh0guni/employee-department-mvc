package fi.solita.exercise.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
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

    public boolean removeEmployee(Employee employee) {
        return employees.remove(employee);
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}
