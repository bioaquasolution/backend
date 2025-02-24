package com.thexbyte.bioaqua.controllers;

import com.thexbyte.bioaqua.entites.Billing;
import com.thexbyte.bioaqua.entites.RecurringBilling;
import com.thexbyte.bioaqua.services.BillingService;
import com.thexbyte.bioaqua.utils.PaymentRequest;
import com.thexbyte.bioaqua.utils.CreateBillingRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billing")
@RequiredArgsConstructor
@Tag(name = "Billing Management", description = "APIs for managing billing and invoices")
public class BillingController {

    private final BillingService billingService;

    @GetMapping
    @Operation(summary = "Get all bills", description = "Retrieves a list of all bills")
    public ResponseEntity<?> getAllBills() {
        return billingService.getAllBills();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get bill by ID", description = "Retrieves a specific bill by its ID")
    public ResponseEntity<?> getBillById(@PathVariable Long id) {
        return billingService.getBillById(id);
    }

    @PostMapping
    @Operation(summary = "Create new bill", description = "Creates a new billing record")
    public ResponseEntity<?> createBill(@RequestBody CreateBillingRequest request) {
        return billingService.createBill(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update bill", description = "Updates an existing billing record")
    public ResponseEntity<?> updateBill(@PathVariable Long id, @RequestBody Billing billing) {
        return billingService.updateBill(id, billing);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete bill", description = "Deletes a billing record")
    public ResponseEntity<?> deleteBill(@PathVariable Long id) {
        return billingService.deleteBill(id);
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get client bills", description = "Retrieves all bills for a specific client")
    public ResponseEntity<?> getClientBills(@PathVariable Long clientId) {
        return billingService.getClientBills(clientId);
    }

    @GetMapping("/system/{systemId}")
    @Operation(summary = "Get system bills", description = "Retrieves all bills for a specific RO system")
    public ResponseEntity<?> getSystemBills(@PathVariable Long systemId) {
        return billingService.getSystemBills(systemId);
    }

    @PostMapping("/{id}/pay")
    @Operation(summary = "Process payment", description = "Process payment for a specific bill")
    public ResponseEntity<?> processPayment(@PathVariable Long id, @RequestBody PaymentRequest payment) {
        return billingService.processPayment(id, payment);
    }

    @GetMapping("/{id}/payment-history")
    @Operation(summary = "Get payment history", description = "Retrieves payment history for a specific bill")
    public ResponseEntity<?> getPaymentHistory(@PathVariable Long id) {
        return billingService.getPaymentHistory(id);
    }

    @PostMapping("/recurring")
    @Operation(summary = "Create recurring billing", description = "Sets up a recurring billing for a system")
    public ResponseEntity<?> createRecurringBilling(@RequestBody RecurringBilling recurringBilling) {
        return billingService.createRecurringBilling(recurringBilling);
    }

    @GetMapping("/recurring/client/{clientId}")
    @Operation(summary = "Get client recurring billings", description = "Retrieves all recurring billings for a client")
    public ResponseEntity<?> getClientRecurringBillings(@PathVariable Long clientId) {
        return billingService.getClientRecurringBillings(clientId);
    }

    @GetMapping("/{id}/generate-invoice")
    @Operation(summary = "Generate invoice PDF", description = "Generates a PDF invoice for a specific bill")
    public ResponseEntity<Resource> generateInvoicePdf(@PathVariable Long id) {
        Resource pdfResource = billingService.generateInvoicePdf(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"invoice.pdf\"")
                .body(pdfResource);
    }

    @PutMapping("/recurring/{id}")
    @Operation(summary = "Update recurring billing", description = "Updates a recurring billing configuration")
    public ResponseEntity<?> updateRecurringBilling(@PathVariable Long id, @RequestBody RecurringBilling recurringBilling) {
        return billingService.updateRecurringBilling(id, recurringBilling);
    }

    @DeleteMapping("/recurring/{id}")
    @Operation(summary = "Delete recurring billing", description = "Deletes a recurring billing configuration")
    public ResponseEntity<?> deleteRecurringBilling(@PathVariable Long id) {
        return billingService.deleteRecurringBilling(id);
    }
} 