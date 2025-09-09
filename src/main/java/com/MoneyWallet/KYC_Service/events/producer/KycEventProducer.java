package com.MoneyWallet.KYC_Service.events.producer;

import com.MoneyWallet.KYC_Service.events.KycEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KycEventProducer {

    private final KafkaTemplate<String, KycEvent> kafkaTemplate;

    private static final String TOPIC = "kyc-events";

    public void publishKycEvent(KycEvent event) {
        kafkaTemplate.send(TOPIC, event.getUserId().toString(), event);
    }
}

