package by.epam.safonenko.pharmacy.entity;

import java.util.Objects;

public class Producer {
    private int id;
    private String country;
    private String name;

    public Producer(){}

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producer)) return false;
        Producer producer = (Producer) o;
        return id == producer.id &&
                Objects.equals(country, producer.country) &&
                Objects.equals(name, producer.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, country, name);
    }
}
