package com.thexbyte.bioaqua.entites;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RecurringBilling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private RoSystem roSystem;
    
    @ManyToOne
    private User client;
    
    private Double amount;
    private String frequency; // MONTHLY, QUARTERLY, YEARLY
    private Date startDate;
    private Date endDate;
    private Boolean isActive = true;
    private String description;
    private Date lastBillingDate;
    private Date nextBillingDate;
} 