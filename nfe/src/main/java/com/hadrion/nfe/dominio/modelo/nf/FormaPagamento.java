package com.hadrion.nfe.dominio.modelo.nf;

public enum FormaPagamento {
	A_VISTA(0,"Pagamento à vista"),
	A_PRAZO(1,"Pagamento a prazo"),
	OUTROS(2,"Outros");
	/*0=Pagamento à vista;
	1=Pagamento a prazo;
	2=Outros	*/
	private int codigo;
	private String descricao;
	FormaPagamento(int codigo,String descricao){
		this.codigo = codigo;
		this.descricao=descricao;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public String descricao(){
		return descricao;
	}
	
}
