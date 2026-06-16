package com.tarefasAPI.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> errosDeValidacao(MethodArgumentNotValidException erro) {
        Map<String, String> erros = new HashMap<>();

        erro.getBindingResult().getAllErrors().forEach((error) -> {
            String campoQueDeuErro = ((FieldError) error).getField();
            String mensagemDeErro = error.getDefaultMessage();

            erros.put(campoQueDeuErro, mensagemDeErro);
        });

        return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TarefaJaCadastradaException.class)
    public ResponseEntity<Map<String, String>> handleConflito(TarefaJaCadastradaException erro) {
        Map<String, String> error = new HashMap<>();

        error.put("erro", erro.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TarefaNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(TarefaNotFoundException erro) {
        Map<String, String> error = new HashMap<>();

        error.put("erro", erro.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleRegraDeNegocio(IllegalStateException erro) {
        Map<String, String> error = new HashMap<>();

        error.put("erro", erro.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}

