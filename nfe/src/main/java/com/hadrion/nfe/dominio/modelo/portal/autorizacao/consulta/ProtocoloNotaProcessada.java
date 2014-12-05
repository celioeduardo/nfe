package com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;

public class ProtocoloNotaProcessada {
	
	private Date dataHoraProcessamento;
	private NumeroProtocolo numero;
	private Mensagem mensagem;
	private ChaveAcesso chaveAcesso;
	
	public ProtocoloNotaProcessada(Date dataHoraProcessamento,
			NumeroProtocolo numero, Mensagem mensagem,
			ChaveAcesso chaveAcesso) {
		super();
		this.dataHoraProcessamento = dataHoraProcessamento;
		this.numero = numero;
		this.mensagem = mensagem;
		this.chaveAcesso = chaveAcesso;
	}
	
	public boolean notaAutorizada(){
		return mensagem.notaAutorizada();
	}
	
	public boolean notaDenegada(){
		return mensagem.notaDenegada();
	}
	
	
	public Date dataHoraProcessamento(){
		return this.dataHoraProcessamento;
	}
	
	public NumeroProtocolo numero(){
		return this.numero;
	}
	
	public Mensagem mensagem(){
		return this.mensagem;
	}
	
	public ChaveAcesso chaveAcesso(){
		return this.chaveAcesso;
	} 
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			ProtocoloNotaProcessada objetoTipado = (ProtocoloNotaProcessada) objeto;
			objetosIguais = new EqualsBuilder()
				.append(dataHoraProcessamento(),objetoTipado.dataHoraProcessamento())
				.append(numero(),objetoTipado.numero())
				.append(mensagem(),objetoTipado.mensagem())
				.append(chaveAcesso(),objetoTipado.chaveAcesso())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(559,17)
			.append(dataHoraProcessamento())
			.append(numero())
			.append(mensagem())
			.append(chaveAcesso())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "ProtocoloNotaProcessada [dataHoraProcessamento=" + dataHoraProcessamento()
				+",numero=" + numero()
				+",mensagem=" + mensagem()
				+",chaveAcesso=" + chaveAcesso()
				+ "]";
	}
	
}
