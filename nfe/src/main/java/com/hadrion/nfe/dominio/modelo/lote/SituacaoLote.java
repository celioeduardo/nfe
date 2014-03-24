package com.hadrion.nfe.dominio.modelo.lote;

enum SituacaoLote {
	NAO_ENVIADO,
	FALHA_CONSISTENCIA,
	ERRO_TRANSMISSAO,
	PROCESSANDO,
	PROCESSADO,
	CANCELADO
}
