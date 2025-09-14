package com.MoneyWallet.KYC_Service.enums;

public enum KycStatus {
    NOT_STARTED,    // user has not submitted anything yet
    IN_PROGRESS,    // collecting docs
    SUBMITTED,      // sent to Smile
    COMPLETED,     // KYC flow finished on your side but not necessarily verified yet
    VERIFIED,       // Smile verified
    REJECTED        // Smile rejected
}
