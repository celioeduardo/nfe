package com.hadrion.nfe.aplicacao;

import com.hadrion.comum.paginacao.Paginacao;


public class PesquisarPaginadoComando {
	private Paginacao paginacao;
	
	public PesquisarPaginadoComando(Paginacao paginacao) {
		super();
		this.paginacao = paginacao;
	}
	
	public Paginacao paginacao(){
		return paginacao;
	}
	
	protected PesquisarPaginadoComando(){}
}
