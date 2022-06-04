package com.vnk.authserver.Service;


import com.vnk.authserver.Model.SmsRequest;

public interface SmsSender {

    void sendSms(SmsRequest smsRequest);

    void sendOtp(SmsRequest smsRequest);

    boolean checkOtp(SmsRequest smsRequest);
}
