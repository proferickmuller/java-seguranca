package br.com.fiap.pessoa.exception.advice;

import br.com.fiap.pessoa.dto.ErrorDTO;
import br.com.fiap.pessoa.exception.PessoaCPFFoundException;
import br.com.fiap.pessoa.exception.PessoaNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Essa anotação indica que a classe deve verificar as exceções retornadas em todos os controllers da lista
 */
@ControllerAdvice(basePackages = "br.com.fiap.pessoa.controller")
public class PessoaControllerAdvice {

    @ResponseBody //define que o retorno desse método estará no corpo da resposta
    @ResponseStatus(HttpStatus.FOUND) //indica que deve ser retornado o erro 404 como status da resposta
    @ExceptionHandler(PessoaCPFFoundException.class) //indica que o método "handleUserNotFound" deve capturar esse tipo de exceção
    public ErrorDTO handleUserNotFound(PessoaCPFFoundException pessoaCPFFoundException) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.FOUND.value());
        errorDTO.setMessage("CPF já cadastrado!");
        errorDTO.setTimestamp(LocalDateTime.now());
        return errorDTO;
    }

    @ResponseBody //define que o retorno desse método estará no corpo da resposta
    @ResponseStatus(HttpStatus.NOT_FOUND) //indica que deve ser retornado o erro 404 como status da resposta
    @ExceptionHandler(PessoaNotFoundException.class) //indica que o método "handleUserNotFound" deve capturar esse tipo de exceção
    public ErrorDTO handleUserNotFound(PessoaNotFoundException userNotFoundException) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorDTO.setMessage("Pessoa não cadastrada.");
        errorDTO.setTimestamp(LocalDateTime.now());
        return errorDTO;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO processValidationError(MethodArgumentNotValidException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder sb = new StringBuilder("Valor inválido para o(s) campo(s):");
        for (FieldError fieldError : fieldErrors) {
            sb.append(" ");
            sb.append(fieldError.getField());
        }
        errorDTO.setMessage(sb.toString());
        errorDTO.setTimestamp(LocalDateTime.now());
        return errorDTO;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }



}