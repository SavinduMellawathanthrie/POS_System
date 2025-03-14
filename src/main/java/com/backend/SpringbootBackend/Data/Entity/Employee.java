package com.backend.SpringbootBackend.Data.Entity;

import com.backend.SpringbootBackend.Configuration.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "employee")

public class Employee {

    @Id
    @Column(unique = true, nullable = false)
    private String id;  // Unique employee ID

    private String name;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String nic;

    @Column(nullable = false)
    private String password;  // Securely store hashed password

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;  // Enum to define roles

    public Employee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
