package com.gft.boleto.dto;

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
public class UpdateBoletoDTO {

    private long idBoleto;
    private BigDecimal valorPago;
    private LocalDateTime dataPagamento;

}