package br.com.fiap.boleto.controller;

import br.com.fiap.boleto.dto.BoletoDTO;
import br.com.fiap.boleto.dto.UpdateBoletoDTO;
import br.com.fiap.boleto.service.BoletoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/boleto")
@Validated
@RestController
public class BoletoController {

    private final BoletoService boletoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoletoDTO novoBoleto(@Valid @RequestBody BoletoDTO boletoDTO) {
        return boletoService.save(boletoDTO);
    }

    @GetMapping("/{idPessoa}/pessoa")
    public ResponseEntity<List<BoletoDTO>> getBoletosByCodigo(@PathVariable long idPessoa) {
        List<BoletoDTO> boletos = boletoService.getBoletosByCodigo(idPessoa);
        if (boletos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boletos);
    }

    @GetMapping("/{idBoleto}")
    public BoletoDTO findById(@PathVariable Long idBoleto) {
        return boletoService.findById(idBoleto);
    }

    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<BoletoDTO>> getBoletosByPessoaId(@PathVariable long pessoaId) {
        List<BoletoDTO> boletos = boletoService.getBoletosByPessoaId(pessoaId);
        if (boletos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(boletos);
    }

    @PatchMapping("/pagar")
    public BoletoDTO editUser(@Valid @RequestBody UpdateBoletoDTO updateBoletoDTO) {
        return boletoService.editBoleto(updateBoletoDTO);
    }

    @DeleteMapping("/{idBoleto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long idBoleto) {
        boletoService.delete(idBoleto);
    }

}