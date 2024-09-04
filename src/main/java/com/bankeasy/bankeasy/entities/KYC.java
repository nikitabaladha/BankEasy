package com.bankeasy.bankeasy.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "kyc")
public class KYC {

    public enum VerificationStatus {
        VERIFIED,
        PENDING,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private UUID userId;

    @OneToOne
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "document_type", length = 50, nullable = false)
    private String documentType;

    @Column(name = "document_number", length = 50, nullable = false)
    private String documentNumber;

    @Column(name = "document_url", length = 255)
    private String documentUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "verified", nullable = false)
    private VerificationStatus verified = VerificationStatus.PENDING;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    // Default constructor
    public KYC() {}

    // Constructor for setting the values, with a check for null `verified` value
    public KYC(User user, String documentType, String documentNumber, String documentUrl, VerificationStatus verified) {
        this.userId = user.getId();
        this.user = user;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.documentUrl = documentUrl;
        this.verified = (verified != null) ? verified : VerificationStatus.PENDING;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public VerificationStatus getVerified() {
        return verified;
    }

    public void setVerified(VerificationStatus verified) {
        this.verified = verified;
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

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
}
