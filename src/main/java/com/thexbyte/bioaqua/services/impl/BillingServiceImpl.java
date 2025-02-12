package com.thexbyte.bioaqua.services.impl;

import com.thexbyte.bioaqua.entites.*;
import com.thexbyte.bioaqua.repositories.BillingRepository;
import com.thexbyte.bioaqua.repositories.PaymentHistoryRepository;
import com.thexbyte.bioaqua.repositories.RecurringBillingRepository;
import com.thexbyte.bioaqua.services.BillingService;
import com.thexbyte.bioaqua.services.PdfGenerationService;
import com.thexbyte.bioaqua.utils.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingServiceImpl implements BillingService {

    private final BillingRepository billingRepository;
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final RecurringBillingRepository recurringBillingRepository;
    private final ResourceLoader resourceLoader;
    private final PdfGenerationService pdfGenerationService;

    @Override
    public ResponseEntity<?> getAllBills() {
        List<Billing> bills = billingRepository.findAll();
        return ResponseEntity.ok(bills);
    }

    @Override
    public ResponseEntity<?> getBillById(Long id) {
        return billingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> createBill(Billing billing) {
        billing.setStatus(BillingStatus.PENDING);
        billing.setBillingDate(new Date());
        return ResponseEntity.ok(billingRepository.save(billing));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateBill(Long id, Billing billing) {
        return billingRepository.findById(id)
                .map(existingBill -> {
                    billing.setId(id);
                    return ResponseEntity.ok(billingRepository.save(billing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteBill(Long id) {
        return billingRepository.findById(id)
                .map(bill -> {
                    billingRepository.delete(bill);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> getClientBills(Long clientId) {
        List<Billing> bills = billingRepository.findByClientId(clientId);
        return ResponseEntity.ok(bills);
    }

    @Override
    public ResponseEntity<?> getSystemBills(Long systemId) {
        List<Billing> bills = billingRepository.findByRoSystemId(systemId);
        return ResponseEntity.ok(bills);
    }

    @Override
    @Transactional
    public ResponseEntity<?> processPayment(Long id, PaymentRequest payment) {
        return billingRepository.findById(id)
                .map(bill -> {
                    PaymentHistory paymentHistory = new PaymentHistory();
                    paymentHistory.setBilling(bill);
                    paymentHistory.setAmount(payment.getAmount());
                    paymentHistory.setPaymentMethod(payment.getPaymentMethod());
                    paymentHistory.setTransactionId(payment.getTransactionId());
                    paymentHistory.setPaymentDate(payment.getPaymentDate());
                    paymentHistory.setPaymentReference(payment.getPaymentReference());
                    paymentHistory.setDescription(payment.getDescription());
                    paymentHistory.setPaymentStatus("SUCCESS");

                    bill.setIsPaid(true);
                    bill.setStatus(BillingStatus.PAID);
                    billingRepository.save(bill);
                    paymentHistoryRepository.save(paymentHistory);

                    return ResponseEntity.ok(paymentHistory);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> getPaymentHistory(Long id) {
        return billingRepository.findById(id)
                .map(bill -> ResponseEntity.ok(bill.getPaymentHistory()))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> createRecurringBilling(RecurringBilling recurringBilling) {
        recurringBilling.setNextBillingDate(recurringBilling.getStartDate());
        return ResponseEntity.ok(recurringBillingRepository.save(recurringBilling));
    }

    @Override
    public ResponseEntity<?> getClientRecurringBillings(Long clientId) {
        List<RecurringBilling> recurringBillings = recurringBillingRepository.findByClientId(clientId);
        return ResponseEntity.ok(recurringBillings);
    }

    @Override
    public Resource generateInvoicePdf(Long id) {
        return billingRepository.findById(id)
                .map(billing -> pdfGenerationService.generateInvoice(billing))
                .orElseThrow(() -> new RuntimeException("Billing not found"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRecurringBilling(Long id, RecurringBilling recurringBilling) {
        return recurringBillingRepository.findById(id)
                .map(existing -> {
                    recurringBilling.setId(id);
                    return ResponseEntity.ok(recurringBillingRepository.save(recurringBilling));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteRecurringBilling(Long id) {
        return recurringBillingRepository.findById(id)
                .map(recurring -> {
                    recurringBillingRepository.delete(recurring);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 