package by.epam.safonenko.pharmacy.entity;

import java.util.Objects;

public class Producer {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producer)) return false;
        Producer producer = (Producer) o;
        return Objects.equals(country, producer.country) &&
                Objects.equals(name, producer.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(country, name);
    }
}
