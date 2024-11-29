package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, String> {

    // Query to fetch top 50 instructors by rating in descending order
    @Query("SELECT i FROM Instructor i ORDER BY i.rating DESC")
    List<Instructor> findTop50ByRating();

    // Query to find an instructor by email
    Optional<Instructor> findByEmail(String email);
}
