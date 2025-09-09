package com.MoneyWallet.KYC_Service.repository;
;
import com.MoneyWallet.KYC_Service.entity.KycVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface KycVerificationRepository extends JpaRepository<KycVerification, Long> {
    Optional<KycVerification> findByUserId(Long userId);
}
