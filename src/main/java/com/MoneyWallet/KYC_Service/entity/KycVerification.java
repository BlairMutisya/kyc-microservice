package com.MoneyWallet.KYC_Service.entity;


import com.MoneyWallet.KYC_Service.enums.KycStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class KycVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String verificationId;

    private String userId;
    private String fullName;
    private String dateOfBirth;
    private String gender;
    private String nationality;
    private String idType;
    private String idNumber;

    private String idFrontImageUrl;
    private String idBackImageUrl;
    private String selfieImageUrl;

    @Enumerated(EnumType.STRING)
    private KycStatus status;

    private String reason;
    private String smileJobId;

    private Instant createdAt;
    private Instant updatedAt;
}

