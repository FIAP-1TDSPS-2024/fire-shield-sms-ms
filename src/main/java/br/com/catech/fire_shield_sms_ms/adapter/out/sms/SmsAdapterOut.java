package br.com.catech.fire_shield_sms_ms.adapter.out.sms;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsPortOut;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class SmsAdapterOut implements SmsPortOut {

    @Value("${SMS_SID}")
    private String accountSid;

    @Value("${SMS_TOKEN}")
    private String authToken;

    @Value("${SMS_NUMERO}")
    private String phoneNumber;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    @Override
    public void enviarSms(Sms sms) {
        Message.creator(
                new PhoneNumber(sms.getNumeroDestino()),
                new PhoneNumber(phoneNumber),
                sms.getMensagem()
        ).create();
    }
}
