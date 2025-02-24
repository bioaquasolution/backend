package com.thexbyte.bioaqua.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class CreateBillingRequest {
    @Schema(description = "ID of the RO System", example = "1")
    private Long roSystemId;

    @Schema(description = "ID of the client", example = "1")
    private Long clientId;

    @Schema(description = "Total amount of the bill", example = "150.00")
    private Double amount;

    @Schema(description = "Invoice number", example = "INV-2024-001")
    private String invoiceNumber;

    @Schema(description = "Date when the bill was created", example = "2024-03-19")
    private Date billingDate;

    @Schema(description = "Due date for the payment", example = "2024-04-19")
    private Date dueDate;

    @Schema(description = "Description of the bill", example = "Monthly maintenance and service charge")
    private String description;

    @Schema(description = "Billing address", example = "123 Client Street")
    private String billingAddress;

    @Schema(description = "List of items in the bill")
    private List<BillingItemRequest> items;

    @Data
    public static class BillingItemRequest {
        @Schema(description = "Description of the item", example = "Monthly Maintenance")
        private String description;

        @Schema(description = "Quantity of the item", example = "1")
        private Integer quantity;

        @Schema(description = "Price per unit", example = "100.00")
        private Double unitPrice;

        @Schema(description = "Tax rate for the item", example = "0.10")
        private Double taxRate;
    }
} 