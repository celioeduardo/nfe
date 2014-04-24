package com.hadrion.nfe.dominio.modelo.inutillizacao;

public class SolicitacaoInutilizacaoId {
	
	private String id; 
	
	public SolicitacaoInutilizacaoId(String id){
		this.id=id;
	}
	
	public String id(){
		return this.id;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			SolicitacaoInutilizacaoId objetoTipado = (SolicitacaoInutilizacaoId) objeto;
			objetosIguais = this.id().equals(objetoTipado.id());
		} 

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		int hashCodeValue = 
				+ (4742 * 3) 
				+ this.id().hashCode();
		return hashCodeValue;
	}
	
	@Override
	public String toString() {
		return "SolicitacaoCancelamentoId [id=" + id + "]";
	}
}
