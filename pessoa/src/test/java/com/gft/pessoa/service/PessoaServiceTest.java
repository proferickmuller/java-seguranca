package com.gft.pessoa.service;

import com.gft.pessoa.converter.DTOConverter;
import com.gft.pessoa.dto.PessoaDTO;
import com.gft.pessoa.exception.PessoaCPFFoundException;
import com.gft.pessoa.model.Pessoa;
import com.gft.pessoa.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

class PessoaServiceTest {

    @Mock
    PessoaService pessoaService;

    // Save a new PessoaDTO when CPF does not exist in the repository
    @Test
    public void test_save_new_pessoa_when_cpf_not_exists() {
        PessoaRepository pessoaRepository = Mockito.mock(PessoaRepository.class);
        DTOConverter dtoConverter = new DTOConverter();
        PessoaService pessoaService = new PessoaService(pessoaRepository);

        PessoaDTO pessoaDTO = new PessoaDTO(
                "John Doe",
                "12345678901",
                LocalDate.of(1990, 1, 1),
                "12345-678",
                "Street",
                "Neighborhood",
                "State",
                "City"
        );
        Mockito.when(pessoaRepository.existsByCpf(pessoaDTO.getCpf())).thenReturn(false);
        Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(Pessoa.convert(pessoaDTO));

        PessoaDTO savedPessoa = pessoaService.save(pessoaDTO);

        Assertions.assertNotNull(savedPessoa);
        Assertions.assertEquals(pessoaDTO.getNome(), savedPessoa.getNome());
        Assertions.assertEquals(pessoaDTO.getCpf(), savedPessoa.getCpf());
    }

    // Throws PessoaCPFFoundException when CPF already exists in the repository
    @Test
    public void test_save_pessoa_throws_exception_when_cpf_exists() {
        // Arrange
        PessoaRepository pessoaRepository = Mockito.mock(PessoaRepository.class);
        DTOConverter dtoConverter = new DTOConverter();
        PessoaService pessoaService = new PessoaService(pessoaRepository);
        PessoaDTO pessoaDTO = new PessoaDTO("John Doe", "12345678901", LocalDate.of(1990, 1, 1), "12345-678", "Street", "Neighborhood", "State", "City");

        Mockito.when(pessoaRepository.existsByCpf(pessoaDTO.getCpf())).thenReturn(true);

        // Act & Assert
        Assertions.assertThrows(PessoaCPFFoundException.class, () -> {
            pessoaService.save(pessoaDTO);
        });
    }

    // Retrieve all Pessoa entities successfully
    @Test
    public void test_retrieve_all_pessoa_entities_successfully() {
        // Arrange
        PessoaRepository pessoaRepository = Mockito.mock(PessoaRepository.class);
        List<Pessoa> pessoas = List.of(new Pessoa(1L, "John Doe", "12345678901", LocalDate.of(1990, 1, 1), "12345-678", "Street 1", "Neighborhood", "State", "City"));
        Mockito.when(pessoaRepository.findAll()).thenReturn(pessoas);
        PessoaService pessoaService = new PessoaService(pessoaRepository);

        // Act
        List<PessoaDTO> result = pessoaService.getAll();

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("John Doe", result.get(0).getNome());
    }



}