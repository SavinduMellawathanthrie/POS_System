package com.backend.SpringbootBackend.Data.Item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "clothing")
public class Clothing extends Item {
    private String color;
    private String size;

    public Clothing() {}

}
