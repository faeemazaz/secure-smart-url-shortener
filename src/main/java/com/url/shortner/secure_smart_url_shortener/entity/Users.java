package com.url.shortner.secure_smart_url_shortener.entity;

import com.url.shortner.secure_smart_url_shortener.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique=true)
    @NotNull(message = "Please enter your email!!")
    private String email;

    @NotNull(message = "Please enter password!!")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please select the role!!")
    private Role role;

    private Instant createdAt = Instant.now();

    public Users() {}

    public Users(Long userId, String email, String password, Role role, Instant createdAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
