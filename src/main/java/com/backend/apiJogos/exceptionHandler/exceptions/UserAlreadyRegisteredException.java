package com.backend.apiJogos.exceptionHandler.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {

  public UserAlreadyRegisteredException() {
    super("Usuario ja cadastrado!");
  }

  public UserAlreadyRegisteredException(String mensagem) {
    super(mensagem);

  }

}
