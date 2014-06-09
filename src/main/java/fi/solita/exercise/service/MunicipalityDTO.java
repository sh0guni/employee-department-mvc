package fi.solita.exercise.service;

public class MunicipalityDTO {

    private long id;

    private String name;

    private MunicipalityDTO() {
    }

    public MunicipalityDTO(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MunicipalityDTO that = (MunicipalityDTO) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
