package com.MoneyWallet.KYC_Service.enums;

public enum KycStatus {
    NOT_STARTED,    // user has not submitted anything yet
    IN_PROGRESS,    // collecting docs
    SUBMITTED,      // sent to Smile
    VERIFIED,       // Smile verified
    REJECTED        // Smile rejected
}
