package com.backend.apiJogos.exceptionHandler.exceptions;

public class StatusException extends RuntimeException{

  public StatusException(){
    super("Conflito de Status!");
  }

  public StatusException(String mensagem){
    super(mensagem);
  }



}
