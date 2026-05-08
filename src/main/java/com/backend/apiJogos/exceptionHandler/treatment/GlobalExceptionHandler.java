package com.backend.apiJogos.exceptionHandler.treatment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.backend.apiJogos.exceptionHandler.exceptions.UserJaCadastradoException;
import com.backend.apiJogos.exceptionHandler.exceptions.UserNotFoundException;
import com.backend.apiJogos.exceptionHandler.exceptions.GameNotFoundException;
import com.backend.apiJogos.exceptionHandler.exceptions.InvalidUserDataException;
import com.backend.apiJogos.exceptionHandler.exceptions.RunEmAndamentoException;

import com.backend.apiJogos.exceptionHandler.formatter.RestErrorMensagem;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private ResponseEntity<RestErrorMensagem> criarResposta(HttpStatus status, String mensagem) {
    return ResponseEntity.status(status).body(new RestErrorMensagem(status.value(), mensagem));
  }

  @ExceptionHandler(UserJaCadastradoException.class)
  ResponseEntity<RestErrorMensagem> UserJaCadastradoExcepition(UserJaCadastradoException ex) {
    return criarResposta(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(UserNotFoundException.class)
  ResponseEntity<RestErrorMensagem> UserNotFoundException(UserNotFoundException ex) {
    return criarResposta(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(GameNotFoundException.class)
  ResponseEntity<RestErrorMensagem> GameNotFoundException(GameNotFoundException ex) {
    return criarResposta(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(InvalidUserDataException.class)
  ResponseEntity<RestErrorMensagem> InvalidUserDataException(InvalidUserDataException ex) {
    return criarResposta(HttpStatus.BAD_REQUEST, ex.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  ResponseEntity<RestErrorMensagem> RuntimeException(RuntimeException ex) {
    return criarResposta(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
  }

  @ExceptionHandler(RunEmAndamentoException.class)
  ResponseEntity<RestErrorMensagem> RunEmAndamentoException(RunEmAndamentoException ex) {
    return criarResposta(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(com.backend.apiJogos.exceptionHandler.exceptions.RunNaoEncontradaException.class)
  ResponseEntity<RestErrorMensagem> RunNaoEncontradaException(
      com.backend.apiJogos.exceptionHandler.exceptions.RunNaoEncontradaException ex) {
    return criarResposta(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(com.backend.apiJogos.exceptionHandler.exceptions.HorasException.class)
  ResponseEntity<RestErrorMensagem> HorasException(
      com.backend.apiJogos.exceptionHandler.exceptions.HorasException ex) {
    return criarResposta(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(com.backend.apiJogos.exceptionHandler.exceptions.DatasException.class)
  ResponseEntity<RestErrorMensagem> DatasException(
      com.backend.apiJogos.exceptionHandler.exceptions.DatasException ex) {
    return criarResposta(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(com.backend.apiJogos.exceptionHandler.exceptions.StatusException.class)
  ResponseEntity<RestErrorMensagem> StatusException(
      com.backend.apiJogos.exceptionHandler.exceptions.StatusException ex) {
    return criarResposta(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(com.backend.apiJogos.exceptionHandler.exceptions.RatingException.class)
  ResponseEntity<RestErrorMensagem> RatingException(
      com.backend.apiJogos.exceptionHandler.exceptions.RatingException ex) {
    return criarResposta(HttpStatus.CONFLICT, ex.getMessage());
  }

}
