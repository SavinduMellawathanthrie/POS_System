package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
    List<Quiz> findByQuizCategory(String quizCategory);
}

