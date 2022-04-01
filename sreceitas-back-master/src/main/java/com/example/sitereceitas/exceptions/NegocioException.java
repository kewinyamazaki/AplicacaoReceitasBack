package com.example.sitereceitas.exceptions;

public class NegocioException extends RuntimeException{

	private static final long serialVersionUID = 6211523017205781198L;

	public NegocioException(String mensagem) {
        super(mensagem);
    }
}
