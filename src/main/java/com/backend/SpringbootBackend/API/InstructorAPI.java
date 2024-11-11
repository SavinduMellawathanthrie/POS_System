package com.backend.SpringbootBackend.API;


import com.backend.SpringbootBackend.Data.Instructor;
import com.backend.SpringbootBackend.Service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
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
        instructorService.addInstructor(instructor);
    }

    @PutMapping("/{id}")
    public void updateInstructor(@PathVariable String id, @RequestBody Instructor instructor) {
        instructorService.updateInstructor(id, instructor);
    }

    @DeleteMapping("/{id}")
    public void deleteInstructor(@PathVariable String id) {
        instructorService.deleteInstructor(id);
    }
}
