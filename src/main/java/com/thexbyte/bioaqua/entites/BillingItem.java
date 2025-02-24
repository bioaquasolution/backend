package com.thexbyte.bioaqua.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BillingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String description;
    private Integer quantity;
    private Double unitPrice;
    private Double total;
    private String unit = "unit";
    private Double taxRate = 0.0;
    private Double taxAmount = 0.0;
    
    @ManyToOne
    @JsonIgnore
    private Billing billing;
} 