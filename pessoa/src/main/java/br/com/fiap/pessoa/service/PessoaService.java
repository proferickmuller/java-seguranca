package br.com.fiap.pessoa.service;

import br.com.fiap.pessoa.client.BoletosClient;
import br.com.fiap.pessoa.client.dto.BoletoDTO;
import br.com.fiap.pessoa.client.dto.PessoaComBoletosDTO;
import br.com.fiap.pessoa.converter.DTOConverter;
import br.com.fiap.pessoa.dto.PessoaDTO;
import br.com.fiap.pessoa.exception.PessoaCPFFoundException;
import br.com.fiap.pessoa.exception.PessoaNotFoundException;
import br.com.fiap.pessoa.model.Pessoa;
import br.com.fiap.pessoa.repository.PessoaRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    private BoletosClient boletosClient;

    //Criar uma nova pessoa
    public PessoaDTO save(@NotNull PessoaDTO pessoaDTO) {
        boolean existsPessoa = pessoaRepository.existsByCpf(pessoaDTO.getCpf());
        if (existsPessoa) {
            throw new PessoaCPFFoundException();
        }
        Pessoa pessoa = pessoaRepository.save(Pessoa.convert(pessoaDTO));
        return DTOConverter.convert(pessoa);
    }

    //Buscar todas as pessoas
    public List<PessoaDTO> getAll() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas
                .stream()
                .map(DTOConverter::convert)
                .toList();
    }

    //Buscar pessoa por ID e obter seus boletos utilizando Open Feign
    public ResponseEntity<PessoaComBoletosDTO> listBoletos(long pessoaId) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaId);
        if (pessoa.isPresent()) {
            /* chamar o serviço pra listar os boletos da pessoa com Open Feign... */
            List<BoletoDTO> boletos = boletosClient.getBoletosByPessoaId(pessoaId);
            PessoaComBoletosDTO pessoaComBoletos = new PessoaComBoletosDTO();
            pessoaComBoletos.setPessoa(DTOConverter.convert(pessoa.get()));
            pessoaComBoletos.setBoletos(boletos);
            return ResponseEntity.ok(pessoaComBoletos);
        } else {
            throw new PessoaNotFoundException();
        }
    }

    //Atualizar os dados da pessoa por ID
    public PessoaDTO editPessoa(Long pessoaId, @NotNull PessoaDTO pessoaDTO) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId).orElseThrow(PessoaNotFoundException::new);
        if (pessoaDTO.getNome() != null &&
                !pessoa.getNome().equals(pessoaDTO.getNome())) {
            pessoa.setNome(pessoaDTO.getNome());
        }
        if (pessoaDTO.getCpf() != null &&
                !pessoa.getCpf().equals(pessoaDTO.getCpf())) {
            pessoa.setCpf(pessoaDTO.getCpf());
        }
        if (pessoaDTO.getDataNascimento() != null &&
                !pessoa.getDataNascimento().equals(pessoaDTO.getDataNascimento())) {
            pessoa.setDataNascimento(pessoaDTO.getDataNascimento());
        }
        if (pessoaDTO.getCep() != null &&
                !pessoa.getCep().equals(pessoaDTO.getCep())) {
            pessoa.setCep(pessoaDTO.getCep());
        }
        if (pessoaDTO.getLogradouro() != null &&
                !pessoa.getLogradouro().equals(pessoaDTO.getLogradouro())) {
            pessoa.setLogradouro(pessoaDTO.getLogradouro());
        }
        if (pessoaDTO.getBairro() != null &&
                !pessoa.getBairro().equals(pessoaDTO.getBairro())) {
            pessoa.setBairro(pessoaDTO.getBairro());
        }
        if (pessoaDTO.getUf() != null &&
                !pessoa.getUf().equals(pessoaDTO.getUf())) {
            pessoa.setUf(pessoaDTO.getUf());
        }
        if (pessoaDTO.getCidade() != null &&
                !pessoa.getCidade().equals(pessoaDTO.getCidade())) {
            pessoa.setCidade(pessoaDTO.getCidade());
        }
        pessoa = pessoaRepository.save(pessoa);
        return DTOConverter.convert(pessoa);
    }

    //Excluir pessoa por ID
    public PessoaDTO delete(long pessoaId) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(pessoaId);
        if (pessoa.isPresent()) {
            pessoaRepository.delete(pessoa.get());
        } else {
            throw new PessoaNotFoundException();
        }
        return null;
    }

    //Gerar o relatório PDF
    public byte[] generateReport(List<PessoaDTO> pessoas) throws JRException {
        // Carrega o arquivo .jrxml e compila para um objeto JasperReport
        InputStream inputStream = getClass().getResourceAsStream("/template.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        // Converte a lista de pessoas em um JRBeanCollectionDataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(pessoas);

        // Define os parâmetros do relatório, se houver
        Map<String, Object> parameters = new HashMap<>();

        // Gera o relatório em formato PDF
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

}