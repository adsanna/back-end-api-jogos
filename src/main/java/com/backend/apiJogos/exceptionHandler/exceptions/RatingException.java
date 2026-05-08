package com.backend.apiJogos.exceptionHandler.exceptions;

public class RatingException extends RuntimeException{

  public RatingException(){
    super("Conflito com avaliação!");
  }

  public RatingException(String mensagem){
    super(mensagem);
  }



}
