package com.hadrion.nfe.dominio.exception;

public class EntidadeNaoEncontradoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradoException(String nomeEntidade, Object identificacao){
		super(nomeEntidade+ " [" + identificacao + "]");
	}
	
}
