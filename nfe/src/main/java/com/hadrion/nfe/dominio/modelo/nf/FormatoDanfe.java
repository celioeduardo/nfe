package com.hadrion.nfe.dominio.modelo.nf;

public enum FormatoDanfe {
	SEM_GERACAO(0,"Sem geração de DANFE"),
	NORMAL_RETRATO(1,"DANFE normal, Retrato"),
	NORMAL_PAISAGEM(2,"DANFE normal, Paisagem"),
	SIMPLIFICADO(3,"DANFE Simplificado"),
	CONSUMIDOR(4,"DANFE NFC-e"),
	CONSUMIDOR_SMS(5,"DANFE NFC-e em mensagem eletrônica");	
	
	/*0=Sem geração de DANFE;
	1=DANFE normal, Retrato;
	2=DANFE normal, Paisagem;
	3=DANFE Simplificado;
	4=DANFE NFC-e;
	5=DANFE NFC-e em mensagem eletrônica (o envio de
	mensagem eletrônica pode ser feita de forma simultânea
	com a impressão do DANFE; usar o tpImp=5 quando
	esta for a única forma de disponibilização do DANFE).*/
	
	private int codigo;
	private String descricao;
	FormatoDanfe(int codigo,String descricao){
		this.codigo = codigo;
		this.descricao=descricao;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String descricao(){
		return descricao;
	}

	public static FormatoDanfe obterPeloCodigo(int codigo) {
		if (codigo == 0) return SEM_GERACAO;
		else if (codigo == 1) return NORMAL_RETRATO;
		else if (codigo == 2) return NORMAL_PAISAGEM;
		else if (codigo == 3) return SIMPLIFICADO;
		else if (codigo == 4) return CONSUMIDOR;
		else if (codigo == 5) return CONSUMIDOR_SMS;
		return null;
	}
}
