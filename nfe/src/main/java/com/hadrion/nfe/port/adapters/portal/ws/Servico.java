package com.hadrion.nfe.port.adapters.portal.ws;

public enum Servico {
	AUTORIZACAO("NFeAutorizacao"),
	RET_AUTORIZACAO("NFeRetAutorizacao"), 
	EVENTO("RecepcaoEvento"), 
	CONSULTA_CADASTRO("NfeConsultaCadastro"), 
	STATUS_SERVICO("NfeStatusServico"), 
	CONSULTA_PROTOCOLO("NfeConsultaProtocolo"),
	INUTILIZACAO("NfeInutilizacao");
	
	String nome;
	
	Servico(String nome){
		this.nome = nome;
	}
}
