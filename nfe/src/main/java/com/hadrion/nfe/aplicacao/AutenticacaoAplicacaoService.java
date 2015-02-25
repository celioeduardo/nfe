package com.hadrion.nfe.aplicacao;

public interface AutenticacaoAplicacaoService {
	
	boolean autenticar(String hospedeId, String username, String senha);
	
}
