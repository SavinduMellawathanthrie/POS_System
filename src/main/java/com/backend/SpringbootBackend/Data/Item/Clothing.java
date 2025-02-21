package com.backend.SpringbootBackend.Data.Item;

import jakarta.persistence.*;

@Entity
@Table(name = "clothing")
public class Clothing extends Item {
    private String color;
    private String size;

    public Clothing() {}

    public Clothing(String c001, String shirt, double v, int i, String men, String blue, String m) {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
