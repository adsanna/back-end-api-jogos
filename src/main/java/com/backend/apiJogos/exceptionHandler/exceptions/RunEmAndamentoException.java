package com.backend.apiJogos.exceptionHandler.exceptions;

public class RunEmAndamentoException extends RuntimeException{

  public RunEmAndamentoException(){
    super("Ja existe uma run em andamento!");
  }

  public RunEmAndamentoException(String mensagem){
    super(mensagem);
  }



}
