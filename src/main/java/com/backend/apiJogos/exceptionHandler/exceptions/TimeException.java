package com.backend.apiJogos.exceptionHandler.exceptions;

public class TimeException extends RuntimeException{

  public TimeException(){
    super("Conflito nas horas informadas.");
  }

  public TimeException(String mensagem){
    super(mensagem);
  }

  
}
