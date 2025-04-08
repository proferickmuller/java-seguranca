package br.com.fiap.boleto.dto;

import br.com.fiap.boleto.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoletoDTO {

    @NotNull(message = "Identificador da Pessoa")
    private long idPessoa;
    @NotNull(message = "Valor do documento é obrigatório")
    private BigDecimal valorDocumento;
    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDateTime dataVencimento;
    private BigDecimal valorPago;
    private LocalDateTime dataPagamento;
    @NotNull(message = "Status é obrigatório")
    private Status status;

}