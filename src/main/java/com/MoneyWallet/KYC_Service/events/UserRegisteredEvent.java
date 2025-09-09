package com.MoneyWallet.KYC_Service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisteredEvent {
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private Long accountTypeId;
}

