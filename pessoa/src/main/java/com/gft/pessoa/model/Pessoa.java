package com.gft.pessoa.model;

import com.gft.pessoa.dto.PessoaDTO;
import com.gft.pessoa.validation.ValidAge;
import com.gft.pessoa.validation.ValidCPF;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @ValidCPF
    @Column(name =  "cpf", nullable = false)
    private String cpf;

    @ValidAge
    @Column(name = "dataNascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "cep")
    private String cep;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "uf")
    private String uf;

    @Column(name = "cidade")
    private String cidade;

    public static Pessoa convert(PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setCpf(pessoaDTO.getCpf());
        pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        pessoa.setCep(pessoaDTO.getCep());
        pessoa.setLogradouro(pessoaDTO.getLogradouro());
        pessoa.setBairro(pessoaDTO.getBairro());
        pessoa.setUf(pessoaDTO.getUf());
        pessoa.setCidade(pessoaDTO.getCidade());
        return pessoa;
    }

}