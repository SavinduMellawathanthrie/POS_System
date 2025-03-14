package com.backend.SpringbootBackend.Service.Entity;

import com.backend.SpringbootBackend.Data.Entity.Employee;
import com.backend.SpringbootBackend.Exception.ServiceRuntimeException;
import com.backend.SpringbootBackend.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@Service
public class EmployeeService {

    private static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Employee> getAllEmployees() {
        LOGGER.info("Fetching all employees...");
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee createEmployee(Employee employee) {
        LOGGER.info("Creating new employee with username: " + employee.getUsername());

        if (employeeRepository.findByUsername(employee.getUsername()).isPresent()) {
            LOGGER.warning("Username already exists: " + employee.getUsername());
            throw new ServiceRuntimeException("Username already exists.");
        }
        if (employeeRepository.findByNic(employee.getNic()).isPresent()) {
            LOGGER.warning("NIC already exists: " + employee.getNic());
            throw new ServiceRuntimeException("NIC already exists.");
        }

        if (employee.getId() == null || employee.getId().isEmpty()) {
            employee.setId(generateEmployeeId());
            LOGGER.info("Generated Employee ID: " + employee.getId());
        }

        Employee savedEmployee = employeeRepository.save(employee);
        LOGGER.info("Successfully created employee: " + savedEmployee.getId());
        return savedEmployee;
    }

    @Transactional
    public Employee updateEmployee(String id, Employee updatedEmployee) {
        LOGGER.info("Updating employee with ID: " + id);

        Employee existingEmployee = getEmployeeById(id);

        if (!existingEmployee.getUsername().equals(updatedEmployee.getUsername()) &&
                employeeRepository.findByUsername(updatedEmployee.getUsername()).isPresent()) {
            LOGGER.warning("Username already exists: " + updatedEmployee.getUsername());
            throw new ServiceRuntimeException("Username already exists.");
        }

        if (!existingEmployee.getNic().equals(updatedEmployee.getNic()) &&
                employeeRepository.findByNic(updatedEmployee.getNic()).isPresent()) {
            LOGGER.warning("NIC already exists: " + updatedEmployee.getNic());
            throw new ServiceRuntimeException("NIC already exists.");
        }

        updatedEmployee.setId(id);
        Employee savedEmployee = employeeRepository.save(updatedEmployee);
        LOGGER.info("Successfully updated employee: " + id);
        return savedEmployee;
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteEmployee(String id) {
        LOGGER.info("Deleting employee with ID: " + id);

        if (!employeeRepository.existsById(id)) {
            LOGGER.warning("Employee not found with ID: " + id);
            throw new ServiceRuntimeException("Employee not found with ID: " + id);
        }

        employeeRepository.deleteById(id);
        LOGGER.info("Successfully deleted employee with ID: " + id);
    }

    public Employee getEmployeeById(String id) {
        LOGGER.info("Fetching employee with ID: " + id);

        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            LOGGER.info("Employee found: " + id);
            return employee.get();
        } else {
            LOGGER.warning("Employee not found with ID: " + id);
            throw new ServiceRuntimeException("Employee not found with ID: " + id);
        }
    }

    private String generateEmployeeId() {
        String id = "E" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        LOGGER.info("Generated new employee ID: " + id);
        return id;
    }
}
