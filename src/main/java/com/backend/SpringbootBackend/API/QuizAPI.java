package com.backend.SpringbootBackend.API;

import com.backend.SpringbootBackend.Data.Quiz;
import com.backend.SpringbootBackend.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizAPI {

    private final QuizService quizService;

    @Autowired
    public QuizAPI(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{quizID}")
    public Quiz getQuizById(@PathVariable String quizID) {
        return quizService.getQuizById(quizID);
    }

    @GetMapping("/category/{category}")
    public List<Quiz> getQuizzesByCategory(@PathVariable String category) {
        return quizService.getQuizzesByCategory(category);
    }

    @PostMapping
    public void addQuiz(@RequestBody Quiz quiz) {
        quizService.addQuiz(quiz);
    }

    @PutMapping("/{quizID}")
    public void updateQuiz(@PathVariable String quizID, @RequestBody Quiz quiz) {
        quizService.updateQuiz(quizID, quiz);
    }

    @DeleteMapping("/{quizID}")
    public void deleteQuiz(@PathVariable String quizID) {
        quizService.deleteQuiz(quizID);
    }
}
