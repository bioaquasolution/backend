package com.thexbyte.bioaqua.utils;

import lombok.Data;
import java.util.Date;

@Data
public class PaymentRequest {
    private String paymentMethod;
    private Double amount;
    private String transactionId;
    private Date paymentDate;
    private String paymentReference;
    private String description;
} 