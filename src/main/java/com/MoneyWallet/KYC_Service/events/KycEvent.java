package com.MoneyWallet.KYC_Service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KycEvent {

    // Core context for every event
    private Long userId;
    private String status;
    private String currentStep;

    // Data fields - these are NULLABLE and only populated if relevant to the eventType
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String idType;
    private String idNumber;
    private String idFrontImageUrl;
    private String idBackImageUrl;
    private String selfieImageUrl;
}