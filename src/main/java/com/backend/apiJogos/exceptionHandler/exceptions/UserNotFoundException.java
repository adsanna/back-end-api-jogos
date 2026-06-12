package com.backend.apiJogos.exceptionHandler.exceptions;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {
    super("Usuário não encontrado!");
  }

  public UserNotFoundException(String mensagem) {
    super(mensagem);
  }

}
