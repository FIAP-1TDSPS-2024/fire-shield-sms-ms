package br.com.catech.fire_shield_sms_ms.adapter.out.persistence;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper.SmsPersistenceMapper;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.SmsJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsRepository;
import org.springframework.stereotype.Component;

public class SmsJpaAdapter implements SmsRepository {

    private final SmsJpaRepository repository;

    public SmsJpaAdapter(SmsJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Sms sms) {
        repository.save(SmsPersistenceMapper.toEntity(sms));
    }
}

