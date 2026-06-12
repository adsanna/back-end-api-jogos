package com.backend.apiJogos.exceptionHandler.exceptions;

public class RatingException extends RuntimeException{

  public RatingException(){
    super("Avaliação invalida!");
  }

  public RatingException(String mensagem){
    super(mensagem);
  }



}
