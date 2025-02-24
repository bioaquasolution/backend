package com.thexbyte.bioaqua.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

@Data
public class RoSystemRequest {
    
    @Schema(description = "Name of the RO system", example = "AquaPure 5000")
    private String name;

    @Schema(description = "Model of the RO system", example = "AP5000")
    private String model;

    @Schema(description = "Unique serial number of the system", example = "SN-12345")
    private String serialNumber;

    @Schema(description = "System capacity in L/day", example = "5000 L/day")
    private String capacity;

    @Schema(description = "Physical dimensions of the system", example = "120x80x50 cm")
    private String dimensions;

    @Schema(description = "Date when the system was installed", example = "2025-01-31T10:30:00.000Z")
    private Date installationDate;

    @Schema(description = "Owner ID associated with the system", example = "123")
    private Long ownerId;
}
