package br.com.fiap.pessoa.client.dto;

import br.com.fiap.pessoa.dto.PessoaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaComBoletosDTO {
    private PessoaDTO pessoa;
    private List<BoletoDTO> boletos;
}