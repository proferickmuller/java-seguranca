package com.gft.boleto.service;

import com.gft.boleto.converter.DTOConverter;
import com.gft.boleto.dto.BoletoDTO;
import com.gft.boleto.dto.UpdateBoletoDTO;
import com.gft.boleto.enums.Status;
import com.gft.boleto.exception.BoletoNotFoundException;
import com.gft.boleto.exception.DeleteNotAllowedException;
import com.gft.boleto.exception.PaymentNotAllowedException;
import com.gft.boleto.model.Boleto;
import com.gft.boleto.repository.BoletoRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;

    private static final Logger logger = LoggerFactory.getLogger(BoletoService.class);

    //Criar uma novo boleto
    public BoletoDTO save(@NotNull BoletoDTO boletoDTO) {
        Boleto boleto = boletoRepository.save(Boleto.convert(boletoDTO));
        boleto.setStatus(Status.PENDENTE); //melhorar com Spring State Machine
        return DTOConverter.convert(boleto);
    }

    //Buscar pelo ID da Pessoa e ordenar pela data de vencimento
    public List<BoletoDTO> getBoletosByCodigo(long idPessoa) {
        return boletoRepository.findByidPessoaOrderByDataVencimentoAsc(idPessoa);
    }

    //Burcar boleto pelo ID
    public BoletoDTO findById(long idBoleto) {
        Boleto boleto = boletoRepository.findById(idBoleto)
                .orElseThrow(BoletoNotFoundException::new);
        return DTOConverter.convert(boleto);
    }

    //Buscar boletos pelo ID da pessoa
    public List<BoletoDTO> getBoletosByPessoaId(long pessoaId) {
        return boletoRepository.findByidPessoa(pessoaId);
    }

    //Pagar boleto
    public BoletoDTO editBoleto(@NotNull UpdateBoletoDTO updateBoletoDTO) {
        Boleto boleto = boletoRepository.findById(updateBoletoDTO.getIdBoleto())
                .orElseThrow(BoletoNotFoundException::new);

        if (boleto.getStatus() == Status.PENDENTE) {
            boleto.setStatus(Status.PAGO);
        } else {
            throw new PaymentNotAllowedException("Boleto não está PENDENTE");
        }

        if (updateBoletoDTO.getValorPago().equals(boleto.getValorDocumento())) {
            boleto.setValorPago(updateBoletoDTO.getValorPago());
        } else {
            throw new PaymentNotAllowedException("Valor a pagar é diferente do valor do documento.");
        }

        if (updateBoletoDTO.getDataPagamento().toLocalDate().equals(LocalDateTime.now().toLocalDate())) {
            boleto.setDataPagamento(updateBoletoDTO.getDataPagamento());
        } else {
            throw new PaymentNotAllowedException("Data de pagamento deve ser igual a data atual.");
        }
        boleto = boletoRepository.save(boleto);
        return DTOConverter.convert(boleto);
    }

    //Excluir boleto por ID
    public ResponseEntity<?> delete(long idBoleto) {
        Optional<Boleto> boleto = Optional.ofNullable(boletoRepository.findById(idBoleto)
                .orElseThrow(BoletoNotFoundException::new));
        if (boleto.get().getStatus() == Status.PAGO) {
            throw new DeleteNotAllowedException("Não é permitada exclusão de um boleto PAGO.");
        } else {
            boletoRepository.delete(boleto.get());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Transactional
    public void validarEBaixarBoletosVencidos() {
        LocalDateTime hoje = LocalDateTime.now();
        String statusPendente = String.valueOf(Status.PENDENTE.ordinal());
        List<Boleto> boletos = boletoRepository.findByDataVencimentoBeforeAndStatus(hoje, statusPendente);
        for (Boleto boleto : boletos) {
            int rowsUpdated = boletoRepository.updateBoletoStatus(
                    String.valueOf(Status.VENCIDO.ordinal()),
                    boleto.getId(),
                    boleto.getIdPessoa()
            );
            if (rowsUpdated > 0) {
                logger.info("Status atualizado com sucesso para o boleto de ID: {} e ID Pessoa: {}",
                        boleto.getId(), boleto.getIdPessoa());
            } else {
                logger.warn("Nenhum registro foi atualizado para o boleto de ID: {} e ID Pessoa: {}",
                        boleto.getId(), boleto.getIdPessoa());
            }
        }
    }

}