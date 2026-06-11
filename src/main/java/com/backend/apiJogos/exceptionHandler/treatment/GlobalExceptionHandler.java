package com.backend.apiJogos.exceptionHandler.treatment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.backend.apiJogos.exceptionHandler.exceptions.UserAlreadyRegisteredException;
import com.backend.apiJogos.exceptionHandler.exceptions.UserNotFoundException;
import com.backend.apiJogos.exceptionHandler.exceptions.AccessDeniedException;
import com.backend.apiJogos.exceptionHandler.exceptions.GameNotFoundException;
import com.backend.apiJogos.exceptionHandler.exceptions.InvalidUserDataException;
import com.backend.apiJogos.exceptionHandler.exceptions.RunInProgressException;

import com.backend.apiJogos.exceptionHandler.formatter.RestErrorMensagem;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private ResponseEntity<RestErrorMensagem> criarResposta(HttpStatus status, String mensagem) {
    return ResponseEntity.status(status).body(new RestErrorMensagem(status.value(), mensagem));
  }

  @ExceptionHandler(UserAlreadyRegisteredException.class)
  ResponseEntity<RestErrorMensagem> UserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
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

  @ExceptionHandler(RunInProgressException.class)
  ResponseEntity<RestErrorMensagem> RunInProgressException(RunInProgressException ex) {
    return criarResposta(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(com.backend.apiJogos.exceptionHandler.exceptions.RunNotFoundException.class)
  ResponseEntity<RestErrorMensagem> RunNotFoundException(
      com.backend.apiJogos.exceptionHandler.exceptions.RunNotFoundException ex) {
    return criarResposta(HttpStatus.NOT_FOUND, ex.getMessage());
  }

  @ExceptionHandler(com.backend.apiJogos.exceptionHandler.exceptions.TimeException.class)
  ResponseEntity<RestErrorMensagem> TimeException(
      com.backend.apiJogos.exceptionHandler.exceptions.TimeException ex) {
    return criarResposta(HttpStatus.CONFLICT, ex.getMessage());
  }

  @ExceptionHandler(com.backend.apiJogos.exceptionHandler.exceptions.DateException.class)
  ResponseEntity<RestErrorMensagem> DateException(
      com.backend.apiJogos.exceptionHandler.exceptions.DateException ex) {
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

  @ExceptionHandler(AccessDeniedException.class)
  ResponseEntity<RestErrorMensagem> AccessDeniedException(AccessDeniedException ex) {
    return criarResposta(HttpStatus.FORBIDDEN, ex.getMessage());
  }

}
