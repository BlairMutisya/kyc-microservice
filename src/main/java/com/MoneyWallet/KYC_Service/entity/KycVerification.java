package com.MoneyWallet.KYC_Service.entity;


import com.MoneyWallet.KYC_Service.enums.KycStatus;
import com.MoneyWallet.KYC_Service.enums.KycStep;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class KycVerification {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    // Step 1 fields
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;

    // Step 2 fields
//    @Enumerated(EnumType.STRING)
    private String idType;
    private String idNumber;
    private String idFrontImageUrl;
    private String idBackImageUrl;

    // Step 3 fields
    private String selfieImageUrl;

    @Enumerated(EnumType.STRING)
    private KycStatus status;

    @Enumerated(EnumType.STRING)
    private KycStep currentStep;

    private String rejectionReason;
}

