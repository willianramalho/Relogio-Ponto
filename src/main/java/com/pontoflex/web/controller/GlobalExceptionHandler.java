package com.pontoflex.web.controller;

import com.pontoflex.domain.exception.CpfJaCadastradoException;
import com.pontoflex.domain.exception.CredenciaisInvalidasException;
import com.pontoflex.domain.exception.EmailJaCadastradoException;
import com.pontoflex.domain.exception.MatriculaJaCadastradaException;
import com.pontoflex.domain.exception.RegraDeNegocioException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        EmailJaCadastradoException.class,
        CpfJaCadastradoException.class,
        MatriculaJaCadastradaException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResponse handleConflito(RegraDeNegocioException ex) {
        return ErroResponse.de(ex.getCodigo(), ex.getMessage());
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResponse handleCredenciaisInvalidas(RegraDeNegocioException ex) {
        return ErroResponse.de(ex.getCodigo(), ex.getMessage());
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResponse handleRegraDeNegocio(RegraDeNegocioException ex) {
        return ErroResponse.de(ex.getCodigo(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse handleValidacao(MethodArgumentNotValidException ex) {
        List<ErroResponse.CampoInvalido> campos = ex.getBindingResult().getFieldErrors().stream()
                .map(this::paraCampoInvalido)
                .toList();
        return ErroResponse.deValidacao(campos);
    }

    private ErroResponse.CampoInvalido paraCampoInvalido(FieldError erro) {
        return new ErroResponse.CampoInvalido(erro.getField(), erro.getDefaultMessage());
    }
}
