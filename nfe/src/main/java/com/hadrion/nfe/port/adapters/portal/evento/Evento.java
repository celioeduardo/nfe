package com.hadrion.nfe.port.adapters.portal.evento;

import static org.apache.commons.lang.StringUtils.leftPad;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.tipos.Cnpj;

public class Evento {
	
	private long tipo;

	private String descricao;

	private Uf uf;
	
	private Ambiente ambiente;
	private Cnpj cnpjAutor;
	private ChaveAcesso chaveAcesso;
	private Date dataHora;
	private long sequencia;
	private NumeroProtocolo numeroProtocolo;
	private String justificativa;
	private String versao;
	
	private Evento(long tipo, String descricao,
			Uf uf, Ambiente ambiente, Cnpj cnpjAutor,
			ChaveAcesso chaveAcesso, Date dataHora, long sequencia,
			NumeroProtocolo numeroProtocolo, String justificativa) {
		super();
		this.tipo = tipo;
		this.descricao = descricao;
		this.uf = uf;
		this.ambiente = ambiente;
		this.cnpjAutor = cnpjAutor;
		this.chaveAcesso = chaveAcesso;
		this.dataHora = dataHora;
		this.sequencia = sequencia;
		this.numeroProtocolo = numeroProtocolo;
		this.justificativa = justificativa;
		this.versao = "1.00";
	}

	public static Evento novoCancelamento(
			Uf uf,
			Ambiente ambiente,
			Cnpj cnpjAutor,
			ChaveAcesso chaveAcesso,
			Date dataHora,
			long sequencia,
			NumeroProtocolo numeroProtocolo,
			String justificativa){
		
		return new Evento(
				110111, "Cancelamento", 
				uf, ambiente, cnpjAutor, 
				chaveAcesso, dataHora, sequencia, numeroProtocolo, 
				justificativa);
	}

	public long tipo(){
		return tipo;
	}
	
	public String descricao(){
		return descricao;
	}

	public String id() {
		return "ID"+tipo()+chaveAcesso()+
			leftPad(String.valueOf(sequencia()), 2, "0");
	}

	public ChaveAcesso chaveAcesso(){
		return chaveAcesso;
	}
	
	public long sequencia(){
		return sequencia;
	}

	public int codigoOrgaoRecepcao() {
		return Integer.valueOf(uf.codigo());
	}

	public Ambiente ambiente() {
		return ambiente;
	}

	public Cnpj cnpjAutor() {
		return cnpjAutor;
	}

	public Date dataHora() {
		return dataHora;
	}

	public NumeroProtocolo numeroProtocolo() {
		return numeroProtocolo;
	}

	public String justificativa() {
		return justificativa;
	}
	
	public String versao(){
		return versao;
	}
	
	public Uf uf(){
		return uf;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Evento objetoTipado = (Evento) objeto;
			objetosIguais = new EqualsBuilder()
				.append(tipo,objetoTipado.tipo)
				.append(descricao,objetoTipado.descricao)
				.append(uf,objetoTipado.uf)
				.append(ambiente,objetoTipado.ambiente)
				.append(cnpjAutor,objetoTipado.cnpjAutor)
				.append(chaveAcesso,objetoTipado.chaveAcesso)
				.append(dataHora,objetoTipado.dataHora)
				.append(sequencia,objetoTipado.sequencia)
				.append(numeroProtocolo,objetoTipado.numeroProtocolo)
				.append(justificativa,objetoTipado.justificativa)
				.append(versao,objetoTipado.versao)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(177,723)
			.append(tipo)
			.append(descricao)
			.append(uf)
			.append(ambiente)
			.append(cnpjAutor)
			.append(chaveAcesso)
			.append(dataHora)
			.append(sequencia)
			.append(numeroProtocolo)
			.append(justificativa)
			.append(versao)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Evento [tipo=" + tipo
				+ ",descricao="+ descricao
				+ ",uf="+ uf
				+ ",ambiente="+ ambiente
				+ ",cnpjAutor="+ cnpjAutor
				+ ",chaveAcesso="+ chaveAcesso
				+ ",dataHora="+ dataHora
				+ ",sequencia="+ sequencia
				+ ",numeroProtocolo="+ numeroProtocolo
				+ ",justificativa="+ justificativa
				+ ",versao="+ versao
				+ "]";
	}
}
