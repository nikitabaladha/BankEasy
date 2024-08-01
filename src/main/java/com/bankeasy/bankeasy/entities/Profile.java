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
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(unique = true, nullable = false)
    private UUID userId;
    
    @OneToOne
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private User user;
    
    @Column(name = "accountId", unique = true, nullable = false)
    private UUID accountId; 
    
    @OneToOne
    @JoinColumn(name = "accountId", nullable = false, insertable = false, updatable = false)
    private Account account;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(length = 10)
    private String phoneNumber;
    
    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    public Profile() {}

    public Profile(User user, Account account, String name, String address, String phoneNumber) {
    	this.userId = user.getId();
    	this.accountId = account.getId();
        this.user = user;
        this.account = account;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getUserId() {
    	return userId;
    }
    
    public UUID getAccountId() {
    	return accountId;
    }

    public void setAccountId(UUID accountId) { this.accountId = accountId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public BigDecimal getAvailableBalance() {
        return account != null ? account.getBalance() : BigDecimal.ZERO;
    }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
