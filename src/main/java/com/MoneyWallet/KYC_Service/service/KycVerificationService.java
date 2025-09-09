package com.MoneyWallet.KYC_Service.service;

import com.MoneyWallet.KYC_Service.dto.request.BasicDetailsRequest;
import com.MoneyWallet.KYC_Service.dto.request.IdDocsRequest;
import com.MoneyWallet.KYC_Service.dto.request.SelfieRequest;
import com.MoneyWallet.KYC_Service.dto.response.KycVerificationResponse;

public interface KycVerificationService {

    KycVerificationResponse saveBasicDetails(BasicDetailsRequest dto);

    KycVerificationResponse saveIdDocs(IdDocsRequest dto);

    KycVerificationResponse saveSelfie(SelfieRequest dto);
}
