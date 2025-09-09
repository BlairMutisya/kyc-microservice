package com.MoneyWallet.KYC_Service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class KycVerificationResponse {
    private Long verificationId;
    private String status;
    private String currentStep;
    private String reason;
}
