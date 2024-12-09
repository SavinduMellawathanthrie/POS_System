package com.backend.SpringbootBackend.Service;

import com.backend.SpringbootBackend.Data.Student;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Repository.StudentRepository;
import com.backend.SpringbootBackend.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            // Log the exception. (IP and MAC spoofing)
            throw new ServiceRuntimeException("Failed to fetch all students", e);
        }
    }

    // Dashboard

    public Student getStudentById(String id) {
        try {
            Optional<Student> student = studentRepository.findById(id);
            return student.orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        } catch (ResourceNotFoundException e) {
            // Logger
            throw e;
        } catch (Exception e) {
            // Log
            throw new ServiceRuntimeException("An error occurred while fetching student by id: " + id, e);
        }
    }

    public void updateStudent(String id, Student updatedStudent) {
        try {
            Student existingStudent = getStudentById(id);
            existingStudent.setName(updatedStudent.getName());
            // Password Update should be done separately.
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setCategories(updatedStudent.getCategories());
            existingStudent.setScore(updatedStudent.getScore());
            studentRepository.save(existingStudent);
        } catch (ResourceNotFoundException e) {
            // Handle the case where the student to update is not found
            throw e;
        } catch (Exception e) {
            // Log the exception and wrap it as a service-level exception
            throw new ServiceRuntimeException("Failed to update student with id: " + id, e);
        }
    }

    public void deleteStudent(String id) {
        try {
            studentRepository.deleteById(id);
        } catch (Exception e) {
            // Log the exception and wrap it as a service-level exception
            throw new ServiceRuntimeException("Failed to delete student with id: " + id, e);
        }
    }

    public List<Student> getTop50Students() {
        try {
            return studentRepository.findTop50ByScore();
        }
        catch (Exception e){
            throw new ServiceRuntimeException("Failed to fetch top 50 students", e);
        }
    }

    // login and Signup

    public void createStudent(Student student) {
        try {
            String studentID = Utilities.entityIDGenerator('S');
            student.setId(studentID);
            student.setScore(0);
            student.setDateJoined(LocalDate.now());
            student.setUsername(Utilities.entityUsernameGenerator(student.getEmail()));
            studentRepository.save(student);
        } catch (Exception e) {
            // Log the exception and rethrow as a service-level exception
            throw new ServiceRuntimeException("Failed to add a new student", e);
        }

    }  // Signup

    public Student login(String email, String password) {
        try {
            Optional<Student> student = studentRepository.findByEmail(email);

            if (student.isPresent()) {
                // Check if the provided password matches the student's password
                if (student.get().getPassword().equals(password)) {
                    return student.get(); // Successful login. Issue the token.
                } else {
                    throw new ServiceRuntimeException("Invalid credentials.");
                    //logs
                }
            } else {
                throw new ResourceNotFoundException("Student not found with email : " + email);
            }
        } catch (Exception e) {
            // Logs
            throw new ServiceRuntimeException("An error occurred during login", e);
        }
    }    //login. JWT issuing only.

    public void updatePassword(String id, Student updatedStudent,String password, String passwordVerified) {
        if (password.equals(passwordVerified)){
            try {
                Student existingStudent = getStudentById(id);
                existingStudent.setPassword(updatedStudent.getPassword());
                studentRepository.save(existingStudent);
            } catch (ResourceNotFoundException e) {
                // Handle the case where the student to update is not found
                throw e;
            } catch (Exception e) {
                // Log the exception and wrap it as a service-level exception
                throw new ServiceRuntimeException("Failed to update student password with id: " + id, e);
            }
        }
        else {
            throw new ServiceRuntimeException("Invalid credentials.");
        }

    }
}
