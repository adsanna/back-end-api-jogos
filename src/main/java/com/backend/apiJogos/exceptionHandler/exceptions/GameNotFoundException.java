package com.backend.apiJogos.exceptionHandler.exceptions;

public class GameNotFoundException extends RuntimeException {

  public GameNotFoundException() {
    super("Game não encontrado!");
  }

  public GameNotFoundException(String mensagem) {
    super(mensagem);
  }

}
