package com.MoneyWallet.KYC_Service.controller;

import com.MoneyWallet.KYC_Service.dto.request.BasicDetailsRequest;
import com.MoneyWallet.KYC_Service.dto.request.IdDocsRequest;
import com.MoneyWallet.KYC_Service.dto.request.SelfieRequest;
import com.MoneyWallet.KYC_Service.dto.response.KycVerificationResponse;
import com.MoneyWallet.KYC_Service.service.KycVerificationService;
import com.MoneyWallet.KYC_Service.common.BaseApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycVerificationService kycService;

    // ------------------- Step 1: Basic Details -------------------
    @PostMapping("/basic-details")
    public ResponseEntity<BaseApiResponse<KycVerificationResponse>> saveBasicDetails(
            @RequestBody BasicDetailsRequest dto) {

        KycVerificationResponse response = kycService.saveBasicDetails(dto);
        return ResponseEntity.ok(BaseApiResponse.success(response, "Basic details saved successfully"));
    }

    // ------------------- Step 2: ID Documents -------------------
    @PostMapping(value = "/id-docs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseApiResponse<KycVerificationResponse>> saveIdDocs(
            @ModelAttribute IdDocsRequest dto) {

        KycVerificationResponse response = kycService.saveIdDocs(dto);
        return ResponseEntity.ok(BaseApiResponse.success(response, "ID documents uploaded successfully"));
    }


    // ------------------- Step 3: Selfie -------------------
    @PostMapping(value = "/selfie", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseApiResponse<KycVerificationResponse>> saveSelfie(
            @ModelAttribute SelfieRequest dto) {

        KycVerificationResponse response = kycService.saveSelfie(dto);
        return ResponseEntity.ok(BaseApiResponse.success(response, "Selfie uploaded successfully"));
    }

}
