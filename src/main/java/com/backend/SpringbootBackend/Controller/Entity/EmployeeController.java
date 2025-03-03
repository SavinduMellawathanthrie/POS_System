package com.backend.SpringbootBackend.Controller.Entity;

import com.backend.SpringbootBackend.Data.Entity.Employee;
import com.backend.SpringbootBackend.Service.Entity.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Get all employees (Only Admins can access)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * Get employee by ID (Admin & Employee can access their own profile)
     */
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.username")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    /**
     * Get employee by NIC (Only Admins can access)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/nic/{nic}")
    public ResponseEntity<Employee> getEmployeeByNic(@PathVariable String nic) {
        return ResponseEntity.ok(employeeService.getEmployeeByNic(nic));
    }

    /**
     * Create a new employee (Only Admins can create employees)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    /**
     * Update employee details (Admin & Employee can update their own data)
     */
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.username")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee updatedEmployee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, updatedEmployee));
    }

    /**
     * Delete an employee (Only Admins can delete employees)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
