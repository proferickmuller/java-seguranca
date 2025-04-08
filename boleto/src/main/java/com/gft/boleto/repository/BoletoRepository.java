package com.gft.boleto.repository;

import com.gft.boleto.dto.BoletoDTO;
import com.gft.boleto.model.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Long> {

    @Query("SELECT new com.gft.boleto.dto.BoletoDTO(b.idPessoa, b.valorDocumento," +
            "b.dataVencimento, b.valorPago, b.dataPagamento, b.status) " +
            "FROM Boleto b WHERE b.idPessoa = :idPessoa ORDER BY b.dataVencimento ASC")
    List<BoletoDTO> findByidPessoaOrderByDataVencimentoAsc(long idPessoa);

    @Query("SELECT new com.gft.boleto.dto.BoletoDTO(b.idPessoa, b.valorDocumento, " +
            "b.dataVencimento, b.valorPago, b.dataPagamento, b.status) " +
            "FROM Boleto b WHERE b.idPessoa = :idPessoa")
    List<BoletoDTO> findByidPessoa(long idPessoa);

    @Query(value = "SELECT b.id, " +
            "b.data_pagamento, " +
            "b.data_vencimento, " +
            "b.id_pessoa, " +
            "b.status, " +
            "b.valor_documento, " +
            "b.valor_pago " +
            "FROM boletos.boleto b " +
            "WHERE DATE(b.data_vencimento) < :date AND b.status = :status", nativeQuery = true)
    List<Boleto> findByDataVencimentoBeforeAndStatus(@Param("date") LocalDateTime date, @Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE boletos.boleto SET status = ?1 WHERE id = ?2 and id_pessoa = ?3", nativeQuery = true)
    int updateBoletoStatus(String status, Long idBoleto, Long idPessoa);

}