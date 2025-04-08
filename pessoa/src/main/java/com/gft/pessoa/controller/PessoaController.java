package com.gft.pessoa.controller;

import com.gft.pessoa.client.dto.PessoaComBoletosDTO;
import com.gft.pessoa.dto.PessoaDTO;
import com.gft.pessoa.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/pessoa")
@Validated
@RestController
public class PessoaController {

    private final PessoaService pessoaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaDTO newPerson(@Valid @RequestBody PessoaDTO pessoaDTO) {
        return pessoaService.save(pessoaDTO);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> getAll() {
        List<PessoaDTO> pessoas = pessoaService.getAll();
        if (pessoas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/{id}/boletos")
    public ResponseEntity<PessoaComBoletosDTO> getBoletos(@PathVariable long id) {
        return pessoaService.listBoletos(id);
    }

    @PatchMapping("/{id}")
    public PessoaDTO editUser(@PathVariable Long id,
                              @RequestBody PessoaDTO pessoaDTO) {
        return pessoaService.editPessoa(id, pessoaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        pessoaService.delete(id);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generateReportPDF() {
        try {
            byte[] pdf = pessoaService.generateReport(pessoaService.getAll());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "pessoas.pdf");
            headers.setContentLength(pdf.length);
            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        } catch (JRException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}