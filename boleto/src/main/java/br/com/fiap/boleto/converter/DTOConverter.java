package br.com.fiap.boleto.converter;

import br.com.fiap.boleto.dto.BoletoDTO;
import br.com.fiap.boleto.model.Boleto;

public class DTOConverter {

    public static BoletoDTO convert(Boleto boleto) {
        BoletoDTO boletoDTO = new BoletoDTO();
        boletoDTO.setIdPessoa(boleto.getIdPessoa());
        boletoDTO.setValorDocumento(boleto.getValorDocumento());
        boletoDTO.setDataVencimento(boleto.getDataVencimento());
        boletoDTO.setValorPago(boleto.getValorPago());
        boletoDTO.setDataPagamento(boleto.getDataPagamento());
        boletoDTO.setStatus(boleto.getStatus());
        return boletoDTO;
    }
}