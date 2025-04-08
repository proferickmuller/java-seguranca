package br.com.fiap.boleto.model;

import br.com.fiap.boleto.dto.BoletoDTO;
import br.com.fiap.boleto.enums.Status;
import br.com.fiap.boleto.validation.ValidDataVencimento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
@Entity
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "id_Pessoa", nullable = false)
    private long idPessoa;

    @Min(1)
    @Column(name = "valor_documento", nullable = false)
    private BigDecimal valorDocumento;

    @ValidDataVencimento
    @Column(name = "data_vencimento", nullable = false)
    private LocalDateTime dataVencimento;

    @Column(name = "valor_pago")
    private BigDecimal valorPago;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "status", nullable = false)
    private Status status;

    public static Boleto convert(BoletoDTO boletoDTO) {
        Boleto boleto = new Boleto();
        boleto.setIdPessoa(boletoDTO.getIdPessoa());
        boleto.setValorDocumento(boletoDTO.getValorDocumento());
        boleto.setDataVencimento(boletoDTO.getDataVencimento());
        boleto.setValorPago(boletoDTO.getValorPago());
        boleto.setDataPagamento(boletoDTO.getDataPagamento());
        boleto.setStatus(boletoDTO.getStatus());
        return boleto;
    }

}