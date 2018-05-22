package by.epam.safonenko.pharmacy.entity;

import java.util.Objects;

public class Card {
    private String number;
    private String code;

    public Card(){
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return Objects.equals(number, card.number) &&
                Objects.equals(code, card.code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(number, code);
    }
}
