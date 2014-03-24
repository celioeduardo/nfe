package com.hadrion.nfe.dominio.modelo.portal.recepcao;

public class RetEnvNfe {
	protected String versao; //Versão do leiaute
	protected int tpAmb; // Identificação do Ambiente: 1 – Produção / 2 - Homologação
	protected String verAplic; //Versão do Aplicativo que recebeu o Lote.	A versão deve ser iniciada com a sigla da UF nos casos de WS próprio ou a sigla SCAN, SVAN ou SVRS nos demais casos.
	protected String cStat; //Código do status da resposta (vide item 5.1.1)
	protected String xMotivo; //Descrição literal do status da resposta 
	protected String cUF; //Código da UF que atendeu a solicitação.
	protected String dhRecibo; //Data e Hora do Recebimento Formato = AAAA-MM-DDTHH:MM:SS Preenchido com data e hora do recebimento do lote.
	//protected 
}
