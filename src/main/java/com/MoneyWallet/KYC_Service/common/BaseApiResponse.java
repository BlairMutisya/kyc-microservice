package com.MoneyWallet.KYC_Service.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // This will exclude null fields from the JSON response
public class BaseApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private String errorCode;

    public static <T> BaseApiResponse<T> success(T data, String message) {
        return BaseApiResponse.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .build();
    }

    // For errors
    public static <T> BaseApiResponse<T> error(String errorCode, String message, T data) {
        return BaseApiResponse.<T>builder()
                .status("error")
                .errorCode(errorCode)
                .message(message)
                .data(data)
                .build();
    }
}