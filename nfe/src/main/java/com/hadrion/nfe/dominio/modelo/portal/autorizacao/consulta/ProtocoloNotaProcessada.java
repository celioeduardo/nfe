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
	private String xml;
	
	public ProtocoloNotaProcessada(Date dataHoraProcessamento,
			NumeroProtocolo numero, Mensagem mensagem,
			ChaveAcesso chaveAcesso,String xml) {
		super();
		this.dataHoraProcessamento = dataHoraProcessamento;
		this.numero = numero;
		this.mensagem = mensagem;
		this.chaveAcesso = chaveAcesso;
		this.xml = xml;
	}
	
	public ProtocoloNotaProcessada(Date dataHoraProcessamento,
			NumeroProtocolo numero, Mensagem mensagem,
			ChaveAcesso chaveAcesso) {
		this(dataHoraProcessamento, numero, mensagem, chaveAcesso, null);
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
				.append(xml(),objetoTipado.xml())
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
			.append(xml())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "ProtocoloNotaProcessada [dataHoraProcessamento=" + dataHoraProcessamento()
				+",numero=" + numero()
				+",mensagem=" + mensagem()
				+",chaveAcesso=" + chaveAcesso()
				+",xml=" + xml()
				+ "]";
	}
	
	public ProtocoloNotaProcessada definirXml(String xml){
		return new ProtocoloNotaProcessada(dataHoraProcessamento, numero, mensagem, chaveAcesso,xml);
	}
	
	public String xml() {
		return xml;
	}
	
}
