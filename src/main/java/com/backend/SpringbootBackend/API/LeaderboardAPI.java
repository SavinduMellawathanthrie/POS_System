package com.backend.SpringbootBackend.API;

import com.backend.SpringbootBackend.Data.Student;
import com.backend.SpringbootBackend.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardAPI {

    private final StudentService studentService;

    @Autowired
    public LeaderboardAPI(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/top50")
    public List<Student> getTop50Students() {
        return studentService.getTop50Students();
    }
}
