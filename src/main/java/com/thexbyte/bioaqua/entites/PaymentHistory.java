package com.thexbyte.bioaqua.entites;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Billing billing;
    
    private Double amount;
    private String transactionId;
    private Date paymentDate;
    private String paymentMethod;
    private String paymentStatus;
    private String paymentReference;
    private String description;
} 