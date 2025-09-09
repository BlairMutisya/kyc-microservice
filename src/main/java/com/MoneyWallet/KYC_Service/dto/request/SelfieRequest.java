package com.MoneyWallet.KYC_Service.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class SelfieRequest {
    private Long userId;
    private MultipartFile selfieImage;
}
