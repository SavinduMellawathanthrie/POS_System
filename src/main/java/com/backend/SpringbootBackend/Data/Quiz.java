package com.backend.SpringbootBackend.Data;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    private String quizID;
    private String quizCategory;
    private int quizScore;
    private String quizLevel;
    private String creatorUserName;
    private String allocatedTime;
    private LocalDate creationDate;
    private String quizBody;
    private String correctAnswers;
    private String incorrectAnswers;

    public Quiz(String quizID, String quizCategory, int quizScore, String quizLevel, String creatorUserName, String allocatedTime, LocalDate creationDate, String quizBody, String correctAnswers, String incorrectAnswers) {
        this.quizID = quizID;
        this.quizCategory = quizCategory;
        this.quizScore = quizScore;
        this.quizLevel = quizLevel;
        this.creatorUserName = creatorUserName;
        this.allocatedTime = allocatedTime;
        this.creationDate = creationDate;
        this.quizBody = quizBody;
        this.correctAnswers = correctAnswers;
        this.incorrectAnswers = incorrectAnswers;
    }

    public Quiz() {
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getQuizCategory() {
        return quizCategory;
    }

    public void setQuizCategory(String quizCategory) {
    }

    public int getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(int quizScore) {
        this.quizScore = quizScore;
    }

    public String getQuizLevel() {
        return quizLevel;
    }

    public void setQuizLevel(String quizLevel) {
        this.quizLevel = quizLevel;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorName) {
        this.creatorUserName = creatorName;
    }

    public String getAllocatedTime() {
        return allocatedTime;
    }

    public void setAllocatedTime(String creatorEmail) {
        this.allocatedTime = creatorEmail;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getQuizBody() {
        return quizBody;
    }

    public void setQuizBody(String quizBody) {
        this.quizBody = quizBody;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public String getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(String incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
}