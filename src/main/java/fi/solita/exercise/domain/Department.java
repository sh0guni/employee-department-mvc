package fi.solita.exercise.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;


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

    public Department(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(final Employee employee) {
        employees.add(employee);
    }

    public boolean removeEmployee(final Employee employee) {
        return employees.remove(employee);
    }

    public int getEmployeeCount() {
        return this.employees.size();
    }
}
