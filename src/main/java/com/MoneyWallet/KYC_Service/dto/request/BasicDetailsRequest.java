package com.MoneyWallet.KYC_Service.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BasicDetailsRequest {
    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String idType;
    private String idNumber;

}
