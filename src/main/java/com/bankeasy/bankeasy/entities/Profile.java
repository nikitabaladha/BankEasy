package com.bankeasy.bankeasy.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDate; 
import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile {

	 @Id
	 @GeneratedValue(strategy = GenerationType.UUID)
	 @Column(name = "id")  
	 private UUID id;

    @Column(unique = true, nullable = false)
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "first_name", nullable = false)  
    private String firstName;
    
    @Column(name = "last_name", nullable = false) 
    private String lastName;

    @Column(name = "phone_number", length = 10)  
    private String phoneNumber;

    @Column(name = "address", columnDefinition = "TEXT")  
    private String address;

    @Column(name = "city", length = 100)  
    private String city;

    @Column(name = "state", length = 100)  
    private String state;

    @Column(name = "country", length = 100) 
    private String country;

    @Column(name = "zip_code", length = 20) 
    private String zipCode;
    
    @Column(name = "marital_status", length = 20)  
    private String maritalStatus;

    @Column(name = "occupation", length = 100)  
    private String occupation;

    @Column(name = "date_of_birth", nullable = false)  
    private LocalDate dateOfBirth; 
    
    @Column(name = "account_type", nullable = false)  
    private String accountType; 

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    public Profile() {}

    public Profile(User user, String firstName,String lastName, LocalDate dateOfBirth, String phoneNumber, String address,  String city, String state, String zipCode, String country, String maritalStatus, String occupation,  String accountType) {
        this.userId = user.getId();
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;      
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.maritalStatus = maritalStatus;
        this.occupation = occupation;
        this.dateOfBirth = dateOfBirth;
        this.accountType = accountType; 
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

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

