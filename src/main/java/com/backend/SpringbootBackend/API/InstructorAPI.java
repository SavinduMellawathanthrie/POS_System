package com.backend.SpringbootBackend.API;

import com.backend.SpringbootBackend.Configuration.PasswordUpdater;
import com.backend.SpringbootBackend.Data.Instructor;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorAPI {

    private final InstructorService instructorService;

    @Autowired
    public InstructorAPI(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public List<Instructor> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public Instructor getInstructorById(@PathVariable String id) {
        return instructorService.getInstructorById(id);
    }

    @PostMapping
    public void addInstructor(@RequestBody Instructor instructor) {
        instructorService.createInstructor(instructor);
    }

    @PutMapping("/{id}")
    public void updateInstructor(@PathVariable String id, @RequestBody Instructor instructor) {
        instructorService.updateInstructor(id, instructor);
    }

    @DeleteMapping("/{id}")
    public void deleteInstructor(@PathVariable String id) {
        instructorService.deleteInstructor(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            Instructor instructor = instructorService.login(email, password);
            return ResponseEntity.ok(instructor);  // Ideally return JWT or session token here
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage()); // Unauthorized status with error message
        }
    }

    @PutMapping("/{id}/update-password")
    public ResponseEntity<String> updatePassword(@PathVariable String id,
                                                 @RequestBody PasswordUpdater passwordUpdater) {
        try {
            // Create an Instructor object for updating purposes
            Instructor updatedInstructor = new Instructor();
            updatedInstructor.setPassword(passwordUpdater.getNewPassword());

            // Call the service to update the password
            instructorService.updatePassword(id, updatedInstructor, passwordUpdater.getNewPassword(), passwordUpdater.getPasswordVerified());

            return ResponseEntity.ok("Password updated successfully.");
        } catch (ServiceRuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }
}
