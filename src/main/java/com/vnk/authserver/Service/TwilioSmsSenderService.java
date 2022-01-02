package com.vnk.authserver.Service;


import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.type.PhoneNumber;
import com.vnk.authserver.Config.TwilioConfiguration;
import com.vnk.authserver.Model.SmsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSenderService implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSenderService.class);

    private final TwilioConfiguration twilioConfiguration;

    com.twilio.rest.verify.v2.Service smsService = com.twilio.rest.verify.v2.Service.creator("catcat").create();

    @Autowired
    public TwilioSmsSenderService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())) {
            PhoneNumber to = new PhoneNumber(smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
            String message = smsRequest.getMessage();
            MessageCreator creator = Message.creator(to, from, message);
            creator.create();
            LOGGER.info("Send sms {}", smsRequest);
        } else {
            throw new IllegalArgumentException(
                    "Phone number [" + smsRequest.getPhoneNumber() + "] is not a valid number"
            );
        }

    }

    @Override
    public void sendOtp(SmsRequest smsRequest) {
        Verification verification = Verification.creator(
                smsService.getSid(),
                smsRequest.getPhoneNumber(),
                "sms").create();
    }

    @Override
    public boolean checkOtp(SmsRequest smsRequest) {
        com.twilio.rest.verify.v2.Service service = com.twilio.rest.verify.v2.Service.fetcher(smsService.getSid()).fetch();
        VerificationCheck verificationCheck = VerificationCheck.creator(
                service.getSid(), smsRequest.getOtp()
        ).setTo(smsRequest.getPhoneNumber()).create();
        return verificationCheck.getValid();
    }


    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
        return true;
    }
}
