package com.bankeasy.bankeasy.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "kyc")
public class KYC {

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

    @Column(name = "document_url",length = 255)
    private String documentUrl; 

    @Column(name = "verified", nullable = false)
    private Boolean verified = Boolean.FALSE;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;


    public KYC() {}

    public KYC(User user, String documentType, String documentNumber, String documentUrl, Boolean verified) {
        this.userId = user.getId();
        this.user = user;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.documentUrl = documentUrl;
        this.verified = verified != null ? verified : Boolean.FALSE;
    }

  public UUID getId() { return id; }
  
  public void setId(UUID id) { this.id = id; }
  
  public UUID getUserId() { return userId; }

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

  public Boolean getVerified() {
      return verified;
  }

  public void setVerified(Boolean verified) {
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
