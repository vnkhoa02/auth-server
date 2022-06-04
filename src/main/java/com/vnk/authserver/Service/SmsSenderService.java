package com.vnk.authserver.Service;

import com.vnk.authserver.Model.SmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderService {

    private final SmsSender smsSender;

    @Autowired
    public SmsSenderService(@Qualifier("twilio") TwilioSmsSenderService smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }

    public void sendOtp(SmsRequest smsRequest) {
        smsSender.sendOtp(smsRequest);
    }

    public boolean checkOtp(SmsRequest smsRequest) {
        return smsSender.checkOtp(smsRequest);
    }
}
