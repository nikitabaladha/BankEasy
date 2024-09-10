package com.bankeasy.bankeasy.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
	
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    public enum TransactionType {
        Debit,
        Credit,
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "transfer_id")
    private UUID transferId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transfer_id", insertable = false, updatable = false)
    private Transfer transfer;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;

    public Transaction() {}

    // Constructor with transferId field
    public Transaction(User user, BigDecimal amount, TransactionType transactionType, String description, Transfer transfer) {
        this.userId = user.getId();
        this.user = user;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.transfer = transfer;
        this.transferId = transfer.getId();
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getTransferId() { return transferId; }
    public void setTransferId(UUID transferId) { this.transferId = transferId; }

    public Transfer getTransfer() { return transfer; }
    public void setTransfer(Transfer transfer) { this.transfer = transfer; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
