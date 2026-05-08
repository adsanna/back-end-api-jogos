package com.backend.apiJogos.exceptionHandler.exceptions;

public class HorasException extends RuntimeException{

  public HorasException(){
    super("conflito com horas!");
  }

  public HorasException(String mensagem){
    super(mensagem);
  }

  
}
