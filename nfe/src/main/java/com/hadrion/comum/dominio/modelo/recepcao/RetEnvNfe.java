package com.hadrion.comum.dominio.modelo.recepcao;

public class RetEnvNfe {
	private String versao; //Versão do leiaute
	private int tpAmb; // Identificação do Ambiente: 1 – Produção / 2 - Homologação
	private String verAplic; //Versão do Aplicativo que recebeu o Lote.	A versão deve ser iniciada com a sigla da UF nos casos de WS próprio ou a sigla SCAN, SVAN ou SVRS nos demais casos.
	private String cStat; //Código do status da resposta (vide item 5.1.1)
	private String xMotivo; //Descrição literal do status da resposta 
	private String cUF; //Código da UF que atendeu a solicitação.
	private String dhRecibo; //Data e Hora do Recebimento Formato = AAAA-MM-DDTHH:MM:SS Preenchido com data e hora do recebimento do lote.
	//private 
}
