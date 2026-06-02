package br.com.catech.fire_shield_sms_ms.application.ports.out;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;

public interface SmsRepository {
    void save(Sms sms);
}
