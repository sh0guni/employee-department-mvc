package fi.solita.exercise.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long departmentIdentifier;

    protected Municipality() {
    }

    public Municipality(String name, long departmentIdentifier) {
        this.name = name;
        this.departmentIdentifier = departmentIdentifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDepartmentIdentifier() {
        return departmentIdentifier;
    }

    public void setDepartmentIdentifier(long departmentIdentifier) {
        this.departmentIdentifier = departmentIdentifier;
    }
}
