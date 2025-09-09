package com.MoneyWallet.KYC_Service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KycEvent {
    private Long userId;
    private String status;   //  SUBMITTED, APPROVED, REJECTED
    private String currentStep; // BASIC_DETAILS, ID_DOCS, SELFIE
}
