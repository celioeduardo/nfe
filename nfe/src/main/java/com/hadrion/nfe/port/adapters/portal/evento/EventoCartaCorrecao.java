package com.hadrion.nfe.port.adapters.portal.evento;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Cnpj;

public class EventoCartaCorrecao extends Evento{

	public EventoCartaCorrecao(Uf uf, Ambiente ambiente, Cnpj cnpjAutor,
			ChaveAcesso chaveAcesso, Date dataHora, long sequencia,
			String correcao) {
		
		super(110110, "Carta de Correcao", uf, ambiente, cnpjAutor, chaveAcesso, dataHora,
				sequencia);
		
		this.correcao = correcao;
		
		this.condicaoUso = "A Carta de Correção é disciplinada pelo § 1º-A do art. 7º do Convênio S/N, "
				+ "de 15 de dezembro de 1970 e pode ser utilizada para regularização de erro ocorrido "
				+ "na emissão de documento fiscal, desde que o erro não esteja relacionado com: "
				+ "I - as variáveis que determinam o valor do imposto tais como: base de cálculo, "
				+ "alíquota, diferença de preço, quantidade, valor da operação ou da prestação; II - a "
				+ "correção de dados cadastrais que implique mudança do remetente ou do destinatário; "
				+ "III - a data de emissão ou de saída.";
	}

	private String correcao;
	private String condicaoUso;

	public String correcao(){
		return this.correcao;
	}
	
	public String condicaoUso(){
		return this.condicaoUso;
	}

	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;
	
		if (objeto != null && this.getClass() == objeto.getClass()) {
			EventoCartaCorrecao objetoTipado = (EventoCartaCorrecao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(tipo(),objetoTipado.tipo())
				.append(descricao(),objetoTipado.descricao())
				.append(uf(),objetoTipado.uf())
				.append(ambiente(),objetoTipado.ambiente())
				.append(cnpjAutor(),objetoTipado.cnpjAutor())
				.append(chaveAcesso(),objetoTipado.chaveAcesso())
				.append(dataHora(),objetoTipado.dataHora())
				.append(sequencia(),objetoTipado.sequencia())
				.append(versao(),objetoTipado.versao())
				.append(correcao(),objetoTipado.correcao())
				.append(condicaoUso(),objetoTipado.condicaoUso())
				.isEquals();
		}
	
		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(961,23)
			.append(tipo())
			.append(descricao())
			.append(uf())
			.append(ambiente())
			.append(cnpjAutor())
			.append(chaveAcesso())
			.append(dataHora())
			.append(sequencia())
			.append(versao())
			.append(correcao())
			.append(condicaoUso())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "EventoCartaCorrecao [tipo=" + tipo()
				+ ",descricao="+ descricao()
				+ ",uf="+ uf()
				+ ",ambiente="+ ambiente()
				+ ",cnpjAutor="+ cnpjAutor()
				+ ",chaveAcesso="+ chaveAcesso()
				+ ",dataHora="+ dataHora()
				+ ",sequencia="+ sequencia()
				+ ",versao="+ versao()
				+ ",correcao="+ correcao()
				+ ",condicaoUso="+ condicaoUso()
				+ "]";
	}

}
