package com.vnk.authserver.Service;

import com.vnk.authserver.Config.TwilioConfiguration;
import org.springframework.stereotype.Service;

@Service
public class PhoneVerificationService {

    private final TwilioConfiguration twilioConfiguration;


    public PhoneVerificationService(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

}
