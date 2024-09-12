//package com.bankeasy.bankeasy.entities;
//
//import jakarta.persistence.*;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.util.Date;
//import java.util.UUID;
//
//@JsonIgnoreProperties({"transfers", "transactions"})
//@Entity
//@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
//public class User {
//
//    public enum UserStatus {
//        Pending,
//        Submitted,
//        Rejected,
//        Approved,
//        
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    @Column(nullable = false)
//    private String firstName;
//
//    @Column(nullable = false)
//    private String lastName;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    // Use the enum for status
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private UserStatus status = UserStatus.Pending;
//
//    @Column(nullable = false)
//    private String salt;
//
//    @Column(updatable = false, nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreationTimestamp
//    private Date createdAt;
//
//    @Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @UpdateTimestamp
//    private Date updatedAt;
//
//    public User() {
//        super();
//    }
//
//    public User(String firstName, String lastName, String email, String password) {
//        super();
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.email = email;
//        this.password = password;
//    }
//
//    public UUID getId() {
//        return id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public UserStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(UserStatus status) {
//        this.status = status;
//    }
//
//    public String getSalt() {
//        return salt;
//    }
//
//    public void setSalt(String salt) {
//        this.salt = salt;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//}


package com.bankeasy.bankeasy.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@JsonIgnoreProperties({"transfers", "transactions"})
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    public enum UserStatus {
        Pending,
        Submitted,
        Rejected,
        Approved,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.Pending;

    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private String role = "User"; // Default value for role

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt;

    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = "User"; // Default role assignment
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
