package com.backend.apiJogos.exceptionHandler.exceptions;

public class DatasException extends RuntimeException{

  public DatasException(){
    super("Conflito com datas!");
  }

  public DatasException(String mensagem){
    super(mensagem);
  }



}
