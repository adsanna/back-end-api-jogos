package com.backend.apiJogos.exceptionHandler.exceptions;


public class RunNaoEncontradaException extends RuntimeException {

  public RunNaoEncontradaException(){
    super("Run não encontrada!");

  }

  public RunNaoEncontradaException(String mensagem){
    super(mensagem);
  }

}
