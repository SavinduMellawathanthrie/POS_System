package com.backend.SpringbootBackend.Service;

import com.backend.SpringbootBackend.Data.Quiz;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Repository.QuizRepository;
import com.backend.SpringbootBackend.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.SpringbootBackend.Data.Instructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final Instructor instructor = new Instructor();

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(String quizID) {
        Optional<Quiz> quiz = quizRepository.findById(quizID);
        return quiz.orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    public List<Quiz> getQuizzesByCategory(String category) {
        try {
            List<Quiz> quizzes = quizRepository.findByQuizCategory(category);
            if (quizzes.isEmpty()) {
                throw new RuntimeException("No quizzes found for category: " + category);
            }
            return quizzes;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch quizzes by category: " + category, e);
        }
    }


    public void addQuiz(Quiz quiz) {
        try {
            String quizID = Utilities.entityIDGenerator('Q');
            quiz.setQuizID(quizID);
            quiz.setCreationDate(LocalDate.now());
            quiz.setCreatorUserName(instructor.getUsername());
            quizRepository.save(quiz);
        } catch (Exception e) {
            // Log the exception and rethrow as a service-level exception
            throw new ServiceRuntimeException("Failed to add a new student", e);
        }
    }

    public void updateQuiz(String quizID, Quiz updatedQuiz) {
        try {
            Quiz existingQuiz = getQuizById(quizID);
            existingQuiz.setQuizCategory(updatedQuiz.getQuizCategory());
            existingQuiz.setQuizBody(updatedQuiz.getQuizBody());
            existingQuiz.setCorrectAnswers(updatedQuiz.getCorrectAnswers());
            existingQuiz.setIncorrectAnswers(updatedQuiz.getIncorrectAnswers());
            existingQuiz.setQuizScore(updatedQuiz.getQuizScore());
            existingQuiz.setQuizLevel(updatedQuiz.getQuizLevel());
            quizRepository.save(existingQuiz);
        } catch (Exception e) {
            // Log the exception and rethrow as a service-level exception
            throw new ServiceRuntimeException("Failed to update quiz  ", e);
        }
    }

    public void deleteQuiz(String quizID) {
        quizRepository.deleteById(quizID);
    }
}
