package com.vnk.authserver.RestController;

import com.vnk.authserver.Model.SmsRequest;
import com.vnk.authserver.Service.SmsSenderService;
import com.vnk.authserver.Util.Constants;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"Sms Services"})
@RequestMapping(Constants.BASE_URL + "sms")
public class SmsController {

    private final SmsSenderService smsSenderService;

    @Autowired
    public SmsController(SmsSenderService smsSenderService) {
        this.smsSenderService = smsSenderService;
    }

    @PostMapping
    public void sendSms(@RequestBody SmsRequest smsRequest) {
        smsSenderService.sendSms(smsRequest);
    }

    @PostMapping("/send-otp")
    public void sendOtp(@RequestBody SmsRequest smsRequest) {
        smsSenderService.sendOtp(smsRequest);
    }

    @PostMapping("/check-otp")
    public boolean checkOtp(@RequestBody SmsRequest smsRequest) {
        return smsSenderService.checkOtp(smsRequest);
    }
}
