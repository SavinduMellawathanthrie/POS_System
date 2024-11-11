package com.backend.SpringbootBackend.Service;

import com.backend.SpringbootBackend.Data.Instructor;
import com.backend.SpringbootBackend.Repository.InstructorRepository;
import com.backend.SpringbootBackend.Utilities;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> getAllInstructors() {
        try {
            return instructorRepository.findAll();
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to retrieve instructors", e);
        }
    }

    public Instructor getInstructorById(String id) {
        try {
            Optional<Instructor> instructor = instructorRepository.findById(id);
            return instructor.orElseThrow(() -> new ResourceNotFoundException("Instructor not found with ID: " + id));
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to retrieve instructor", e);
        }
    }

    public void addInstructor(Instructor instructor) {
        try {
            String instructorID = Utilities.entityIDGenerator('I');
            instructor.setId(instructorID);
            instructor.setRating(0);
            instructor.setDateJoined(LocalDate.now());
            instructorRepository.save(instructor);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to add instructor", e);
        }
    }

    public void updateInstructor(String id, Instructor updatedInstructor) {
        try {
            Instructor existingInstructor = getInstructorById(id);
            existingInstructor.setName(updatedInstructor.getName());
            existingInstructor.setPassword(updatedInstructor.getPassword());
            existingInstructor.setEmail(updatedInstructor.getEmail());
            existingInstructor.setCategories(updatedInstructor.getCategories());
            existingInstructor.setRating(updatedInstructor.getRating());
            instructorRepository.save(existingInstructor);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to update instructor", e);
        }
    }

    public void deleteInstructor(String id) {
        try {
            instructorRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to delete instructor", e);
        }
    }
}
