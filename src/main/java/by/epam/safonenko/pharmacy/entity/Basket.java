package by.epam.safonenko.pharmacy.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Basket {
    private Map<Medicine, Integer> content;

    public Basket(){
        content = new HashMap<>();

    }

    public Map<Medicine, Integer> getContent() {
        return content;
    }

    public void setContent(Map<Medicine, Integer> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Basket)) return false;
        Basket basket = (Basket) o;
        return Objects.equals(content, basket.content);
    }

    @Override
    public int hashCode() {

        return Objects.hash(content);
    }
}
