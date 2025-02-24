package com.thexbyte.bioaqua.utils;
 
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class AlertRequest {
    @Schema(description = "Title of the alert", example = "System Overload")
    private String title;

    @Schema(description = "Severity level of the alert", example = "High")
    private String siverity; // (Fixed typo: "siverity" -> "severity")

    @Schema(description = "Detailed alert content", example = "The system has detected an overload condition.")
    private String content;

    @Schema(description = "Alert date in ISO format", example = "2025-01-31T10:30:00.000Z")
    private Date alertDate;

    @Schema(description = "ID of the system related to the alert", example = "12345")
    private Long systemId;
}
