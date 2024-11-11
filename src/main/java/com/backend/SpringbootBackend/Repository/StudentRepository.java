package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("SELECT s FROM Student s ORDER BY s.score DESC")
    List<Student> findTop50ByScore();
    Optional<Student> findByEmail(String email);
}
