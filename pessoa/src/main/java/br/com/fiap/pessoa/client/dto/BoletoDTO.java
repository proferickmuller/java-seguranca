package br.com.fiap.pessoa.client.dto;

import br.com.fiap.pessoa.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BoletoDTO(long idPessoa,
                        BigDecimal valorDocumento,
                        LocalDateTime dataVencimento,
                        BigDecimal valorPago,
                        LocalDateTime dataPagamento,
                        Status status) {
}