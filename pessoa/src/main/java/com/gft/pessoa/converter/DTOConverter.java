package com.gft.pessoa.converter;

import com.gft.pessoa.dto.PessoaDTO;
import com.gft.pessoa.model.Pessoa;

public class DTOConverter {

    public static PessoaDTO convert(Pessoa pessoa) {
        PessoaDTO pessoaDTO = new PessoaDTO();
        pessoaDTO.setNome(pessoa.getNome());
        pessoaDTO.setCpf(pessoa.getCpf());
        pessoaDTO.setDataNascimento(pessoa.getDataNascimento());
        pessoaDTO.setCep(pessoa.getCep());
        pessoaDTO.setLogradouro(pessoa.getLogradouro());
        pessoaDTO.setBairro(pessoa.getBairro());
        pessoaDTO.setUf(pessoa.getUf());
        pessoaDTO.setCidade(pessoa.getCidade());
        return pessoaDTO;
    }
}