package ma.amarghad.books_sma.entities;

import java.io.Serializable;

public class Book implements Serializable {
    private long id;
    private String title;
    private double price;

    public Book(long id, String title, double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Book() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return title;
    }
}
