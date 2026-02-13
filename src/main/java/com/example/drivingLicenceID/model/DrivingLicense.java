package com.example.drivingLicenceID.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;

@Entity(name = "drivingLicenseEntity")
@Table(name = "driving_licenses")

@SQLDelete(sql = "UPDATE driving_licenses SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DrivingLicense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid First Name")
    @Column(name = "first_name", length = 20, nullable = false)
    private String name;

    @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "Invalid Family Name")
    @Column(name = "family_name", length = 20, nullable = false)
    private String family;

    @Column(name = "license_number", length = 30, nullable = false, unique = true)
    private String drivingLicenseNumber;

    @Column(name = "certificate_date", nullable = false)
    private LocalDate certificateDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "city_issued", length = 80, nullable = false)
    private String cityIssued;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
}
