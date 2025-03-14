package com.backend.SpringbootBackend.Repository;

import com.backend.SpringbootBackend.Data.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByUsername(String username);
    Optional<Employee> findByNic(String nic);
}
