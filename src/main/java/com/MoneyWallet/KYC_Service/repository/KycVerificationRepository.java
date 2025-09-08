package com.MoneyWallet.KYC_Service.repository;
;
import com.MoneyWallet.KYC_Service.entity.KycVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KycVerificationRepository extends JpaRepository<KycVerification, String> {
}