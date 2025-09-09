package com.MoneyWallet.KYC_Service.service.impl;

import com.MoneyWallet.KYC_Service.dto.request.BasicDetailsRequest;
import com.MoneyWallet.KYC_Service.dto.request.IdDocsRequest;
import com.MoneyWallet.KYC_Service.dto.request.SelfieRequest;
import com.MoneyWallet.KYC_Service.dto.response.KycVerificationResponse;
import com.MoneyWallet.KYC_Service.entity.KycVerification;
import com.MoneyWallet.KYC_Service.enums.KycStatus;
import com.MoneyWallet.KYC_Service.enums.KycStep;
import com.MoneyWallet.KYC_Service.events.KycEvent;
import com.MoneyWallet.KYC_Service.events.producer.KycEventProducer;
import com.MoneyWallet.KYC_Service.repository.KycVerificationRepository;
import com.MoneyWallet.KYC_Service.service.FileStorageService;
import com.MoneyWallet.KYC_Service.service.KycVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class KycVerificationServiceImpl implements KycVerificationService {

    private final KycVerificationRepository repository;
    public final FileStorageService fileStorageService;
    private final KycEventProducer kycEventProducer;

    @Override
    public KycVerificationResponse saveBasicDetails(BasicDetailsRequest dto) {
        KycVerification kyc = repository.findByUserId(dto.getUserId())
                .orElseGet(() -> KycVerification.builder()
                        .userId(dto.getUserId())
                        .status(KycStatus.IN_PROGRESS)
                        .currentStep(KycStep.BASIC_DETAILS)
                        .build());


        kyc.setStatus(KycStatus.IN_PROGRESS);
        kyc.setCurrentStep(KycStep.BASIC_DETAILS);

        kyc.setFullName(dto.getFullName());
        kyc.setDateOfBirth(dto.getDateOfBirth());
        kyc.setGender(dto.getGender());
        kyc.setNationality(dto.getNationality());
        kyc.setIdType(dto.getIdType());
        kyc.setIdNumber(dto.getIdNumber());
        kyc.setCurrentStep(KycStep.BASIC_DETAILS);

        repository.save(kyc);

        // --- Publish event ---
        KycEvent event = new KycEvent(kyc.getUserId(), kyc.getStatus().name(), kyc.getCurrentStep().name());
        kycEventProducer.publishKycEvent(event);

        return toResponse(kyc);
    }


    @Override
    public KycVerificationResponse saveIdDocs(IdDocsRequest dto) {
        KycVerification kyc = repository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("KYC record not found for user " + dto.getUserId()));

        // store images and get URLs
        String frontUrl = fileStorageService.storeFile(dto.getIdFrontImage(), "id-front");
        String backUrl = fileStorageService.storeFile(dto.getIdBackImage(), "id-back");

        kyc.setIdFrontImageUrl(frontUrl);
        kyc.setIdBackImageUrl(backUrl);
        kyc.setCurrentStep(KycStep.ID_DOCS);

        repository.save(kyc);

        // --- Publish event ---
        KycEvent event = new KycEvent(kyc.getUserId(), kyc.getStatus().name(), kyc.getCurrentStep().name());
        kycEventProducer.publishKycEvent(event);
        return toResponse(kyc);
    }

    @Override
    public KycVerificationResponse saveSelfie(SelfieRequest dto) {
        KycVerification kyc = repository.findByUserId(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("KYC record not found for user " + dto.getUserId()));

        String selfieUrl = fileStorageService.storeFile(dto.getSelfieImage(), "selfies");

        kyc.setSelfieImageUrl(selfieUrl);
        kyc.setCurrentStep(KycStep.SELFIE);

        // If all pieces present, trigger Smile ID

        if (isAllDataPresent(kyc)) {
            kyc.setStatus(KycStatus.SUBMITTED);
            kyc.setCurrentStep(KycStep.COMPLETED);
        } else {
            kyc.setStatus(KycStatus.IN_PROGRESS);
        }

        repository.save(kyc);

        // --- Publish event ---
        KycEvent event = new KycEvent(kyc.getUserId(), kyc.getStatus().name(), kyc.getCurrentStep().name());
        kycEventProducer.publishKycEvent(event);

        return toResponse(kyc);
    }

    private boolean isAllDataPresent(KycVerification kyc) {
        return kyc.getFullName() != null &&
                kyc.getDateOfBirth() != null &&
                kyc.getGender() != null &&
                kyc.getNationality() != null &&
                kyc.getIdType() != null &&
                kyc.getIdNumber() != null &&
                kyc.getIdFrontImageUrl() != null &&
                kyc.getIdBackImageUrl() != null &&
                kyc.getSelfieImageUrl() != null;
    }

    private KycVerificationResponse toResponse(KycVerification kyc) {
        return KycVerificationResponse.builder()
                .verificationId(kyc.getId())
                .status(kyc.getStatus().name())
                .currentStep(kyc.getCurrentStep().name())
                .reason(kyc.getRejectionReason())
                .build();
    }
}
