package fi.solita.exercise.service;

public class DepartmentDTO {

    private long id;

    private String name;

    public DepartmentDTO(long id, String name) {
        this.id = id;
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
