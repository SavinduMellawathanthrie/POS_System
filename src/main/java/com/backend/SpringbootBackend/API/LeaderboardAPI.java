package com.backend.SpringbootBackend.API;

import com.backend.SpringbootBackend.Data.Instructor;
import com.backend.SpringbootBackend.Data.Student;
import com.backend.SpringbootBackend.Service.InstructorService;
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
    private final InstructorService instructorService;

    @Autowired
    public LeaderboardAPI(StudentService studentService, InstructorService instructorService) {
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    @GetMapping("/top50/students")
    public List<Student> getTop50Students() {
        return studentService.getTop50Students();
    }

    @GetMapping("/top50/instructors")
    public List<Instructor> getTop50Instructors() {
        return instructorService.getTop50Instructors();
    }
}
