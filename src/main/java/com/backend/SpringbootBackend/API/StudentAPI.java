package com.backend.SpringbootBackend.API;

import com.backend.SpringbootBackend.Configuration.PasswordUpdater;
import com.backend.SpringbootBackend.Data.Student;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentAPI {

    private final StudentService studentService;

    @Autowired
    public StudentAPI(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public void createStudent(@RequestBody Student student) {
        studentService.createStudent(student);
    }

    @PutMapping("/{id}")
    public void updateStudent(@PathVariable String id, @RequestBody Student student) {
        studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Student student = studentService.login(email, password);
            return ResponseEntity.ok(student);  // Ideally return JWT or session token here
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage()); // Unauthorized status with error message
        }
    }

    @PutMapping("/{id}/update-password")
    public ResponseEntity<String> updatePassword(@PathVariable String id,
                                                 @RequestBody PasswordUpdater passwordUpdater) {
        try {
            // Create a Student object for updating purposes
            Student updatedStudent = new Student();
            updatedStudent.setPassword(passwordUpdater.getNewPassword());

            // Call the service to update the password
            studentService.updatePassword(id, updatedStudent, passwordUpdater.getNewPassword(), passwordUpdater.getPasswordVerified());

            return ResponseEntity.ok("Password updated successfully.");
        } catch (ServiceRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
}
