package com.backend.SpringbootBackend.Service.Entity;

import com.backend.SpringbootBackend.Configuration.Role;
import com.backend.SpringbootBackend.Data.Entity.Employee;
import com.backend.SpringbootBackend.Exception.ResourceNotFoundException;
import com.backend.SpringbootBackend.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
    }

    public Employee getEmployeeByNic(String nic) {
        return employeeRepository.findByNic(nic)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with NIC: " + nic));
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        // Hash the password before saving
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        // Ensure role is valid (Default to ROLE_USER if not set)
        if (employee.getRole() == null) {
            employee.setRole(Role.ROLE_USER);
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(String id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setNic(updatedEmployee.getNic());
        existingEmployee.setRole(updatedEmployee.getRole());

        // Only update password if provided
        if (updatedEmployee.getPassword() != null && !updatedEmployee.getPassword().isEmpty()) {
            existingEmployee.setPassword(passwordEncoder.encode(updatedEmployee.getPassword()));
        }

        return employeeRepository.save(existingEmployee);
    }

    @Transactional
    public void deleteEmployee(String id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }
}
