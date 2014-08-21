package com.hadrion.nfe.dominio.modelo.nf.transporte;

public enum ModalidadeFrete {
	EMITENTE(0), 
	DESTINATARIO_REMETENTE(1),
	TERCEIROS(2),
	SEM_FRETE(9);
	
	ModalidadeFrete(int codigo) {
		this.codigo = codigo;
	}
	
	private int codigo;
	
	public int codigo(){
		return codigo;
	}
}
