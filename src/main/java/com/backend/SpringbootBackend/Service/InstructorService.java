package com.backend.SpringbootBackend.Service;

import com.backend.SpringbootBackend.Data.Instructor;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Repository.InstructorRepository;
import com.backend.SpringbootBackend.Utilities;
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

    // Retrieve all instructors
    public List<Instructor> getAllInstructors() {
        try {
            return instructorRepository.findAll();
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch all instructors", e);
        }
    }

    // Retrieve an instructor by ID
    public Instructor getInstructorById(String id) {
        try {
            Optional<Instructor> instructor = instructorRepository.findById(id);
            return instructor.orElseThrow(() -> new ResourceNotFoundException("Instructor not found with ID: " + id));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceRuntimeException("An error occurred while fetching instructor by ID: " + id, e);
        }
    }

    // Add a new instructor
    public void createInstructor(Instructor instructor) {
        try {
            String instructorID = Utilities.entityIDGenerator('I');
            instructor.setId(instructorID);
            instructor.setRating(0);
            instructor.setDateJoined(LocalDate.now());
            instructor.setUsername(Utilities.entityUsernameGenerator(instructor.getEmail()));
            instructorRepository.save(instructor);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to add a new instructor", e);
        }
    }

    // Update an existing instructor
    public void updateInstructor(String id, Instructor updatedInstructor) {
        try {
            Instructor existingInstructor = getInstructorById(id);
            existingInstructor.setName(updatedInstructor.getName());
            existingInstructor.setEmail(updatedInstructor.getEmail());
            existingInstructor.setCategories(updatedInstructor.getCategories());
            existingInstructor.setRating(updatedInstructor.getRating());
            instructorRepository.save(existingInstructor);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to update instructor with ID: " + id, e);
        }
    }

    // Delete an instructor by ID
    public void deleteInstructor(String id) {
        try {
            instructorRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to delete instructor with ID: " + id, e);
        }
    }

    // Retrieve the top 50 instructors by rating
    public List<Instructor> getTop50Instructors() {
        try {
            return instructorRepository.findTop50ByRating();
        } catch (Exception e) {
            throw new ServiceRuntimeException("Failed to fetch top 50 instructors", e);
        }
    }

    // Login functionality for instructors
    public Instructor login(String email, String password) {
        try {
            Optional<Instructor> instructor = instructorRepository.findByEmail(email);

            if (instructor.isPresent()) {
                if (instructor.get().getPassword().equals(password)) {
                    return instructor.get();
                } else {
                    throw new ServiceRuntimeException("Invalid credentials.");
                }
            } else {
                throw new ResourceNotFoundException("Instructor not found with email: " + email);
            }
        } catch (Exception e) {
            throw new ServiceRuntimeException("An error occurred during login", e);
        }
    }

    // Update password functionality
    public void updatePassword(String id, Instructor updatedInstructor, String password, String passwordVerified) {
        if (password.equals(passwordVerified)) {
            try {
                Instructor existingInstructor = getInstructorById(id);
                existingInstructor.setPassword(updatedInstructor.getPassword());
                instructorRepository.save(existingInstructor);
            } catch (ResourceNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new ServiceRuntimeException("Failed to update instructor password with ID: " + id, e);
            }
        } else {
            throw new ServiceRuntimeException("Passwords do not match.");
        }
    }
}
