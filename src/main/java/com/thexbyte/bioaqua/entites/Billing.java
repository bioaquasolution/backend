package com.thexbyte.bioaqua.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private RoSystem roSystem;

    @ManyToOne
    private User client;
    
    private Double amount;
    private String invoiceNumber;
    private Date billingDate;
    private Date dueDate;
    private Boolean isPaid = false;
    
    @Enumerated(EnumType.STRING)
    private BillingStatus status = BillingStatus.PENDING;
    
    private String paymentMethod;
    private String description;
    
    @OneToMany(mappedBy = "billing", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PaymentHistory> paymentHistory = new ArrayList<>();
    
    @ManyToOne
    @JsonIgnore
    private RecurringBilling recurringBilling;
    
    // Fields for invoice generation
    private String billingAddress;
    private String shippingAddress;
    private Double taxAmount;
    private Double subtotal;
    private Double total;
    private String currency = "USD";
    
    @OneToMany(cascade = CascadeType.ALL)
     private List<BillingItem> items = new ArrayList<>();
} 