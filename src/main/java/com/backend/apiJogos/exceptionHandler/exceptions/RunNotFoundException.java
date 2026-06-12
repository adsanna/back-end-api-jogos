package com.backend.apiJogos.exceptionHandler.exceptions;


public class RunNotFoundException extends RuntimeException {

  public RunNotFoundException(){
    super("Run não encontrada!");

  }

  public RunNotFoundException(String mensagem){
    super(mensagem);
  }

}
