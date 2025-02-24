package com.thexbyte.bioaqua.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.thexbyte.bioaqua.entites.Billing;
import com.thexbyte.bioaqua.entites.BillingItem;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
public class PdfGenerationService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public Resource generateInvoice(Billing billing) {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();
            addInvoiceHeader(document, billing);
            addBillingDetails(document, billing);
            addItemsTable(document, billing);
            addTotals(document, billing);
            document.close();

            return new ByteArrayResource(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice PDF", e);
        }
    }

    private void addInvoiceHeader(Document document, Billing billing) throws DocumentException {
        Paragraph title = new Paragraph("INVOICE", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);

        // Company Details
        addCell(headerTable, "BioAqua Solutions", HEADER_FONT);
        addCell(headerTable, "Invoice #: " + billing.getInvoiceNumber(), NORMAL_FONT);
        addCell(headerTable, "123 Water Street", NORMAL_FONT);
        addCell(headerTable, "Date: " + formatDate(billing.getBillingDate()), NORMAL_FONT);
        addCell(headerTable, "City, Country", NORMAL_FONT);
        addCell(headerTable, "Due Date: " + formatDate(billing.getDueDate()), NORMAL_FONT);

        document.add(headerTable);
        document.add(Chunk.NEWLINE);
    }

    private void addBillingDetails(Document document, Billing billing) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        // Bill To section
        addCell(table, "Bill To:", HEADER_FONT);
        addCell(table, "RO System Details:", HEADER_FONT);
        addCell(table, billing.getRoSystem().getOwner().getUsername(), NORMAL_FONT);
        addCell(table, "System: " + billing.getRoSystem().getName(), NORMAL_FONT);
        addCell(table, billing.getBillingAddress(), NORMAL_FONT);
        addCell(table, "Model: " + billing.getRoSystem().getModel(), NORMAL_FONT);

        document.add(table);
        document.add(Chunk.NEWLINE);
    }

    private void addItemsTable(Document document, Billing billing) throws DocumentException {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        float[] columnWidths = {10f, 40f, 15f, 15f, 20f};
        table.setWidths(columnWidths);

        // Add Headers
        addCell(table, "#", HEADER_FONT);
        addCell(table, "Description", HEADER_FONT);
        addCell(table, "Quantity", HEADER_FONT);
        addCell(table, "Unit Price", HEADER_FONT);
        addCell(table, "Total", HEADER_FONT);

        // Add Items
        int itemNumber = 1;
        for (BillingItem item : billing.getItems()) {
            addCell(table, String.valueOf(itemNumber++), NORMAL_FONT);
            addCell(table, item.getDescription(), NORMAL_FONT);
            addCell(table, String.valueOf(item.getQuantity()), NORMAL_FONT);
            addCell(table, formatCurrency(item.getUnitPrice()), NORMAL_FONT);
            addCell(table, formatCurrency(item.getTotal()), NORMAL_FONT);
        }

        document.add(table);
    }

    private void addTotals(Document document, Billing billing) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(40);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);

        addCell(table, "Subtotal:", HEADER_FONT);
        addCell(table, formatCurrency(billing.getSubtotal()), NORMAL_FONT);

        addCell(table, "Tax:", HEADER_FONT);
        addCell(table, formatCurrency(billing.getTaxAmount()), NORMAL_FONT);

        addCell(table, "Total:", HEADER_FONT);
        addCell(table, formatCurrency(billing.getTotal()), NORMAL_FONT);

        document.add(table);
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setBorderWidth(0.5f);
        table.addCell(cell);
    }

    private String formatDate(java.util.Date date) {
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    private String formatCurrency(Double amount) {
        return String.format("$%.2f", amount);
    }
} 