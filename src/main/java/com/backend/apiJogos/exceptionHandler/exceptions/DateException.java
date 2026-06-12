package com.backend.apiJogos.exceptionHandler.exceptions;

public class DateException extends RuntimeException{

  public DateException(){
    super("Conflito entre datas informadas!");
  }

  public DateException(String mensagem){
    super(mensagem);
  }



}
