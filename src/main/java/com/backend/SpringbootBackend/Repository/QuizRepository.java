package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
    // Additional custom query methods can be defined here if needed
}

