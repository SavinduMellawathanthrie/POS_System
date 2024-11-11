package com.backend.SpringbootBackend.Data;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "instructor")
public class Instructor extends Person {

    private float rating;

    public Instructor(String id, String name, String username, String password, String email, String categories, LocalDate dateJoined, float rating) {
        super(id, name, username, password, email, categories, dateJoined);
        this.rating = rating;
    }

    public Instructor() {}

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
