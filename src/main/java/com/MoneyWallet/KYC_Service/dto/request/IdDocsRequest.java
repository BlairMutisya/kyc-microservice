package com.MoneyWallet.KYC_Service.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class IdDocsRequest {
    private Long userId;
    private MultipartFile idFrontImage;
    private MultipartFile idBackImage;
}
