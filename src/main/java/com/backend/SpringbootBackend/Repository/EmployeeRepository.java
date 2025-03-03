package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByNic(String nic);
    Optional<Employee> findById(String id);
}
