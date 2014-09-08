package com.hadrion.nfe.dominio.modelo.nf.publico;
/**

1=Contribuinte ICMS (informar a IE do destinatário);
2=Contribuinte isento de Inscrição no cadastro de
Contribuintes do ICMS;
Nota Fiscal eletrônica
Nota Técnica 2013.005
Pág. 37 / 107
# ID Campo Descrição Ele Pai Tipo Ocor. Tam. Observação
9=Não Contribuinte, que pode ou não possuir Inscrição
Estadual no Cadastro de Contribuintes do ICMS.
Nota 1: No caso de NFC-e informar indIEDest=9 e não
informar a tag IE do destinatário;
Nota 2: No caso de operação com o Exterior informar
indIEDest=9 e não informar a tag IE do destinatário;
Nota 3: No caso de Contribuinte Isento de Inscrição
(indIEDest=2), não informar a tag IE do destinatário.
 */
public enum IndicadorIe {
	CONTRIBUINTE(1),
	ISENTO(2),
	NAO_CONTRIBUINTE(9);
	
	private int codigo;
	
	IndicadorIe(int codigo){
		this.codigo = codigo;
	}
	
	public int codigo(){
		return codigo;
	}
	
	public static IndicadorIe obterPeloCodigo(int valor){
		if (valor==1)
			return CONTRIBUINTE;
		else if (valor==2)
			return ISENTO;
		else if (valor==9)
			return NAO_CONTRIBUINTE;
		return null;
	}

}
