package com.MoneyWallet.KYC_Service.dto.request;

import lombok.Data;

@Data
public class KycVerificationRequest {
    private String userId;
    private String fullName;
    private String dateOfBirth;  // yyyy-MM-dd
    private String gender;
    private String nationality;
    private String idType;
    private String idNumber;
    private String idFrontImage; // base64
    private String idBackImage;
    private String selfieImage;
}
