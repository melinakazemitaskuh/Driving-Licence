package com.example.drivingLicenceID.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DrivingLicenseDto {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid First name")
    private String name;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Family name")
    private String family;

    @NotNull(message = "Driving License Number is required")
    private String drivingLicenseNumber;

    @NotNull(message = "Certificate Date is required")
    private LocalDate certificateDate;

    @NotNull(message = "Expiry Date is required")
    private LocalDate expiryDate;

    @NotNull(message = "City Issued is required")
    private String cityIssued;
}
