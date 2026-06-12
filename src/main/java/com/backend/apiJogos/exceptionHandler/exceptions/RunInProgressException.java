package com.backend.apiJogos.exceptionHandler.exceptions;

public class RunInProgressException extends RuntimeException{

  public RunInProgressException(){
    super("Ja existe uma run em andamento!");
  }

  public RunInProgressException(String mensagem){
    super(mensagem);
  }



}
