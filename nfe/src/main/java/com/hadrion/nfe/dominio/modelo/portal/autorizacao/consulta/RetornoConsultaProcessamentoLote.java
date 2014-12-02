package com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta;

import java.util.List;

import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.MensagemSefaz;

public class RetornoConsultaProcessamentoLote {
	
	private Mensagem mensagem;
	private MensagemSefaz mensagemSefaz;
	private List<ProtocoloNotaProcessada> protocolos;
	
	public RetornoConsultaProcessamentoLote(Mensagem mensagem,
			MensagemSefaz mensagemSefaz,
			List<ProtocoloNotaProcessada> protocolos) {
		super();
		this.mensagem = mensagem;
		this.mensagemSefaz = mensagemSefaz;
		this.protocolos = protocolos;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}
	
	public MensagemSefaz mensagemSefaz(){
		return mensagemSefaz;
	}
	
	public List<ProtocoloNotaProcessada> protocolos(){
		return protocolos;
	}

	public boolean loteProcessadoComSucesso() {
		return false;
	}
	
	public boolean loteFoiProcessado() {
		return mensagem.codigo() ==  104;
	}

	public boolean loteEmProcessamento() {
		return mensagem.codigo() ==  105;
	}
}
