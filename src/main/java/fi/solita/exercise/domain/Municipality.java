package fi.solita.exercise.domain;

import javax.persistence.*;

@Entity
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    protected Municipality() {
    }

    public Municipality(String name) {
            this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
