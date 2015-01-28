package com.hadrion.nfe.dominio.modelo.portal.evento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;

public class RetornoLoteEvento {
	
	private Mensagem mensagem;
	List<RetornoEvento> eventos;
	
	public RetornoLoteEvento(
			Mensagem mensagem,
			RetornoEvento ... eventos) {
		this(mensagem,Arrays.asList(eventos));
	}
	public RetornoLoteEvento(Mensagem mensagem) {
		this(mensagem,new ArrayList<RetornoEvento>());
	}
	
	public RetornoLoteEvento(
			Mensagem mensagem,
			List<RetornoEvento> eventos) {
		super();
		this.mensagem = mensagem;
		this.eventos = eventos;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}

	public boolean loteEventoProcessado() {
		return mensagem.loteEventoProcessado();
	}
	
	private List<RetornoEvento> getEventos(){
		if (eventos == null)
			eventos = new ArrayList<RetornoEvento>();
		return eventos;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			RetornoLoteEvento objetoTipado = (RetornoLoteEvento) objeto;
			objetosIguais = new EqualsBuilder()
				.append(mensagem,objetoTipado.mensagem)
				.append(eventos,objetoTipado.eventos)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(97,213)
			.append(mensagem)
			.append(eventos)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Evento [mensagem=" + mensagem
				+ ",eventos="+ eventos
				+ "]";
	}
	public boolean sucesso() {
		return mensagem().codigo() == 128 || mensagem().codigo() == 135;
	}
	
	public RetornoEvento retornoDaNota(ChaveAcesso chaveAcesso) {
		for (RetornoEvento retorno : getEventos()) {
			if (chaveAcesso.equals(retorno.chaveAcesso()))
				return retorno;
		}
		return null;
	}
	
}
