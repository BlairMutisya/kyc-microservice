package com.MoneyWallet.KYC_Service.events.consumer;

import com.MoneyWallet.KYC_Service.entity.KycVerification;
import com.MoneyWallet.KYC_Service.enums.KycStatus;
import com.MoneyWallet.KYC_Service.enums.KycStep;
import com.MoneyWallet.KYC_Service.events.UserRegisteredEvent;
import com.MoneyWallet.KYC_Service.repository.KycVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredConsumer {

    private final KycVerificationRepository kycRepo;

    @KafkaListener(topics = "user-registered-topic", groupId = "kyc-service-group")
    public void consume(UserRegisteredEvent event) {
        // 1. Check if KYC already exists for this user
        if (kycRepo.findByUserId(event.getUserId()).isEmpty()) {
            KycVerification kyc = KycVerification.builder()
                    .userId(event.getUserId())
                    .fullName(event.getFirstName() + " " +
                            (event.getMiddleName() != null ? event.getMiddleName() + " " : "") +
                            event.getLastName())
                    .status(KycStatus.NOT_STARTED)
                    .currentStep(KycStep.BASIC_DETAILS)
                    .build();

            kycRepo.save(kyc);
        }
    }
}
