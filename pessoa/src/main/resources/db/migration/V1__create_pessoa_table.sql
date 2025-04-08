create schema if not exists pessoas;

create table pessoas.pessoa (
    id bigserial primary key,
    nome varchar(100) not null,
    cpf varchar(11) not null,
    data_nascimento timestamp not null,
    cep varchar(8),
    logradouro varchar(100),
    bairro varchar(50),
    uf varchar(2),
    cidade varchar(50)
);