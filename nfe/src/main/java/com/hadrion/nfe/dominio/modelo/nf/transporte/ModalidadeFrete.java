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

	public static ModalidadeFrete obterPeloCodigo(int codigo) {
		if (codigo == 0) return EMITENTE;
		if (codigo == 1) return DESTINATARIO_REMETENTE;
		if (codigo == 2) return TERCEIROS;
		if (codigo == 9) return SEM_FRETE;
		return null;
	}
}
