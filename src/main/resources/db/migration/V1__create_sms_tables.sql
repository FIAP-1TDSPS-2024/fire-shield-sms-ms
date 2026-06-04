CREATE TABLE sms_contato (
                             uuid RAW(16) NOT NULL,
                             nome VARCHAR2(120) NOT NULL,
                             descricao VARCHAR2(255),
                             numero VARCHAR2(20) NOT NULL,
                             estado VARCHAR2(80) NOT NULL,

                             CONSTRAINT pk_sms_contato PRIMARY KEY (uuid)
);

CREATE TABLE sms_ocorrencia (
                                uuid RAW(16) NOT NULL,
                                latitude NUMBER NOT NULL,
                                longitude NUMBER NOT NULL,
                                severidade VARCHAR2(20) NOT NULL,
                                horario_deteccao TIMESTAMP NOT NULL,
                                nome VARCHAR2(120),
                                bairro VARCHAR2(80),
                                cidade VARCHAR2(80),
                                estado VARCHAR2(100) NOT NULL,
                                cep VARCHAR2(9),

                                CONSTRAINT pk_sms_ocorrencia PRIMARY KEY (uuid)
);

CREATE TABLE sms_sms (
                         uuid RAW(16) NOT NULL,
                         numero_origem VARCHAR2(20) NOT NULL,
                         numero_destino VARCHAR2(20) NOT NULL,
                         mensagem VARCHAR2(500) NOT NULL,
                         data_envio TIMESTAMP NOT NULL,
                         uuid_ocorrencia RAW(16) NOT NULL,
                         uuid_contato RAW(16) NOT NULL,

                         CONSTRAINT pk_sms_sms PRIMARY KEY (uuid),

                         CONSTRAINT fk_sms_ocorrencia
                             FOREIGN KEY (uuid_ocorrencia)
                                 REFERENCES sms_ocorrencia (uuid),

                         CONSTRAINT fk_sms_contato
                             FOREIGN KEY (uuid_contato)
                                 REFERENCES sms_contato (uuid)
);

CREATE INDEX idx_sms_sms_ocorrencia
    ON sms_sms (uuid_ocorrencia);

CREATE INDEX idx_sms_sms_contato
    ON sms_sms (uuid_contato);

CREATE INDEX idx_sms_contato_estado
    ON sms_contato (estado);