CREATE TABLE sms_usuario (
                             uuid RAW(16) NOT NULL,
                             username VARCHAR2(80) NOT NULL,
                             password VARCHAR2(255) NOT NULL,
                             nome VARCHAR2(120) NOT NULL,
                             ativo NUMBER(1) DEFAULT 1 NOT NULL,

                             CONSTRAINT pk_sms_usuario PRIMARY KEY (uuid),
                             CONSTRAINT uk_sms_usuario_username UNIQUE (username)
);

