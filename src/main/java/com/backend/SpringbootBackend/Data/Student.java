package com.backend.SpringbootBackend.Data;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "student")
public class Student extends Person {

    private int score;

    public Student() {}

    public Student(String id, String name, String username, String password, String email, String categories, LocalDate dateJoined, int score) {
        super(id, name, username, password, email, categories, dateJoined);
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score; }

    // Getters and Setters
}
