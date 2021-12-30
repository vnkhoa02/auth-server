package com.vnk.authserver.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SmsRequest {

    private final String phoneNumber;

    private final String message;

    private final String otp;
}
