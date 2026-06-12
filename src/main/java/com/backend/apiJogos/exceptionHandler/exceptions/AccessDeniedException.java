package com.backend.apiJogos.exceptionHandler.exceptions;

public class AccessDeniedException extends RuntimeException{

  public AccessDeniedException(){
    super("Você não possui permissão para executar esta ação!");
  }

  public AccessDeniedException(String mensagem){
    super(mensagem);
  }



}
