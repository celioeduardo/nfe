package com.hadrion.nfe.dominio.modelo.nf;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;

/**
 * 
	1=Emissão normal (não em contingência);
	2=Contingência FS-IA, com impressão do DANFE em
	formulário de segurança;
	3=Contingência SCAN (Sistema de Contingência do
	Ambiente Nacional);
	4=Contingência DPEC (Declaração Prévia da Emissão
	em Contingência);
	5=Contingência FS-DA, com impressão do DANFE em
	formulário de segurança;
	6=Contingência SVC-AN (SEFAZ Virtual de Contingência
	do AN);
	7=Contingência SVC-RS (SEFAZ Virtual de Contingência
	do RS);
	9=Contingência off-line da NFC-e;
 */

public enum TipoEmissao {
	NORMAL(1),
	FS_IA(2),
	SCAN(3),
	DPEC(4),
	FS_DA(5),
	SVC_AN(6),
	SVC_RS(7),
	OFFLINE_NFCE(9);
	
	private int codigo;
	TipoEmissao(int codigo){
		this.codigo = codigo;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public static TipoEmissao obterPeloCodigo(int codigo){
		for (TipoEmissao tipo : TipoEmissao.values()) 
			if (codigo == tipo.codigo)
				return tipo;
		return null;
	}
	
	public static TipoEmissao contingenciaPelaUf(Uf uf){
		switch (uf) {
		case AC:
		case AL:
		case AP: 
		case DF: 
		case ES:
		case MG: 
		case PB:
		case RJ: 
		case RN: 
		case RO: 
		case RR: 
		case RS: 
		case SC: 
		case SE: 
		case SP: 
		case TO :
			return TipoEmissao.SVC_AN;
		default:
			return TipoEmissao.SVC_RS;
		}
	}
	
	public boolean contingencia(){
		return this == TipoEmissao.SVC_AN || this == TipoEmissao.SVC_RS; 
	}
	
}
