package com.thexbyte.bioaqua.services;

import com.thexbyte.bioaqua.entites.Billing;
import com.thexbyte.bioaqua.entites.RecurringBilling;
import com.thexbyte.bioaqua.utils.PaymentRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface BillingService {
    ResponseEntity<?> getAllBills();
    ResponseEntity<?> getBillById(Long id);
    ResponseEntity<?> createBill(Billing billing);
    ResponseEntity<?> updateBill(Long id, Billing billing);
    ResponseEntity<?> deleteBill(Long id);
    ResponseEntity<?> getClientBills(Long clientId);
    ResponseEntity<?> getSystemBills(Long systemId);
    ResponseEntity<?> processPayment(Long id, PaymentRequest payment);
    ResponseEntity<?> getPaymentHistory(Long id);
    ResponseEntity<?> createRecurringBilling(RecurringBilling recurringBilling);
    ResponseEntity<?> getClientRecurringBillings(Long clientId);
    Resource generateInvoicePdf(Long id);
    ResponseEntity<?> updateRecurringBilling(Long id, RecurringBilling recurringBilling);
    ResponseEntity<?> deleteRecurringBilling(Long id);
} 