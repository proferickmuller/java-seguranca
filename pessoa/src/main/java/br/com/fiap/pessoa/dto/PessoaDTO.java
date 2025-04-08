package br.com.fiap.pessoa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {

    @NotNull(message = "Nome é obrigatório")
    private String nome;
    @NotNull(message = "CPF é obrigatório")
    private String cpf;
    @NotNull(message = "Data de Nascimento é obrigatória")
    private LocalDate dataNascimento;
    private String cep;
    private String logradouro;
    private String bairro;
    private String uf;
    private String cidade;

}