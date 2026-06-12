package com.backend.apiJogos.exceptionHandler.exceptions;

public class InvalidUserDataException extends RuntimeException {

  public InvalidUserDataException() {
    super("Dados do usuário inválidos!");
  }

  public InvalidUserDataException(String mensagem) {
    super(mensagem);
  }

}
