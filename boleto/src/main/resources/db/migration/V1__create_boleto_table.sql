create schema if not exists boletos;

create table boletos.boleto (
    id bigserial primary key,
    id_Pessoa bigserial not null REFERENCES pessoas.pessoa(id),
    valor_documento float not null,
    data_vencimento timestamp not null,
    valor_pago float,
    data_pagamento timestamp,
    status varchar(10) not null
);