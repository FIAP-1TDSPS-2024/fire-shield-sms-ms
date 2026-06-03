CREATE TABLE IF NOT EXISTS sms_ocorrencia (
    uuid             UUID         NOT NULL PRIMARY KEY,
    latitude         DOUBLE PRECISION NOT NULL,
    longitude        DOUBLE PRECISION NOT NULL,
    severidade       VARCHAR(20)  NOT NULL,
    horario_deteccao TIMESTAMP    NOT NULL,
    nome             VARCHAR(120),
    bairro           VARCHAR(80),
    cidade           VARCHAR(80),
    estado           VARCHAR(100) NOT NULL,
    cep              VARCHAR(9)
);

CREATE TABLE IF NOT EXISTS sms_contato (
    id               UUID    PRIMARY KEY,
    nome             VARCHAR(120) NOT NULL,
    telefone         VARCHAR(20)  NOT NULL,
    estado           VARCHAR(100) NOT NULL,
    ativo            BOOLEAN      NOT NULL DEFAULT TRUE
);

