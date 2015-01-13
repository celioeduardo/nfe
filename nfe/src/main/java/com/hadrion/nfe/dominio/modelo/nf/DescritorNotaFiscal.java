package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;

public class DescritorNotaFiscal {
	private NotaFiscalId notaFiscalId;
	private String tipo;
	private Cnpj empresa;
	private Cnpj filial;
	private Long numero; 
	private Serie serie;
	private ChaveAcesso chave;
	private TipoEmissao tipoEmissao;
	private Date emissao; 
	private Date dataHora;
	private String publicoTipo;
	private Long publicoCodigo;
	private String publicoNome;
	private Dinheiro valor;
	private Mensagem mensagem;
	
	@SuppressWarnings("unused")
	private DescritorNotaFiscal() {
		super();
	}

	public DescritorNotaFiscal(NotaFiscalId notaFiscalId,
			String tipo,
			Cnpj empresa,
			Cnpj filial,
			Long numero, 
			Serie serie,
			ChaveAcesso chave,
			TipoEmissao tipoEmissao,
			Date emissao, 
			Date dataHora,
			String publicoTipo,
			Long publicoCodigo,
			String publicoNome,
			Dinheiro valor,
			Mensagem mensagem){
	
		this.notaFiscalId = notaFiscalId;
		this.tipo = tipo;
		this.empresa = empresa;
		this.filial = filial;
		this.numero = numero;
		this.serie = serie;
		this.chave = chave;
		this.tipoEmissao = tipoEmissao;
		this.emissao = emissao;
		this.dataHora = dataHora;
		this.publicoTipo = publicoTipo;
		this.publicoCodigo = publicoCodigo;
		this.publicoNome = publicoNome;
		this.valor = valor;
		this.mensagem = mensagem;
	}
	public DescritorNotaFiscal(NotaFiscalId notaFiscalId,
			String tipo,
			Cnpj empresa,
			Cnpj filial,
			Long numero, 
			Serie serie,
			ChaveAcesso chave,
			TipoEmissao tipoEmissao,
			Date emissao, 
			Date dataHora,
			String publicoTipo,
			Long publicoCodigo,
			String publicoNome,
			Dinheiro valor){
		this(notaFiscalId, 
				tipo, empresa, filial, numero, serie, chave, tipoEmissao, emissao, dataHora, 
				publicoTipo, publicoCodigo, publicoNome, valor, null);

	}
	
	public NotaFiscalId notaFiscalId(){
		return this.notaFiscalId;
	}
	public Serie serie() {
		return serie;
	}
	public Long numero() {
		return numero;
	}
	public Date emissao() {
		return emissao;
	}
	public Date dataHora() {
		return dataHora;
	}
	public String publicoTipo(){
		return publicoTipo;
	}
	public String publicoNome(){
		return publicoNome;
	}
	public Long publicoCodigo(){
		return publicoCodigo;
	}
	public String tipo(){
		return tipo;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			DescritorNotaFiscal objetoTipado = (DescritorNotaFiscal) objeto;
			
			objetosIguais = new EqualsBuilder()
			.append(notaFiscalId, objetoTipado.notaFiscalId)
			.append(serie, objetoTipado.serie)
			.append(numero,  objetoTipado.numero)
			.append(emissao, objetoTipado.emissao)
			.append(chave, objetoTipado.chave)
			.append(dataHora, objetoTipado.dataHora)
			.append(tipo, objetoTipado.tipo)
			.append(empresa, objetoTipado.empresa)
			.append(filial, objetoTipado.filial)
			.append(publicoTipo, objetoTipado.publicoTipo)
			.append(publicoCodigo, objetoTipado.publicoCodigo)
			.append(publicoNome, objetoTipado.publicoNome)
			.append(valor, objetoTipado.valor)
			.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3237,1367) 
				.append(notaFiscalId)
				.append(serie)
				.append(numero)
				.append(chave)
				.append(emissao)
				.append(dataHora)			
				.append(tipo)
				.append(empresa)
				.append(filial)
				.append(publicoTipo)
				.append(publicoCodigo)
				.append(publicoNome)
				.append(valor)
				.toHashCode();		
	}
	
	@Override
	public String toString() {
		return "DescritorNotaFiscal [notaFiscalId=" + notaFiscalId
				+ ",serie=" + serie
				+ ",numero=" + numero
				+ ",emissao=" + emissao
				+ ",chave=" + chave
				+ ",dataHora=" + dataHora 
				+ ",tipo=" + tipo 
				+ ",empresa=" + empresa 
				+ ",filial=" + filial 
				+ ",publicoTipo=" + publicoTipo 
				+ ",publicoCodigo=" + publicoCodigo 
				+ ",publicoNome=" + publicoNome
				+ ",valor=" + valor
				+ "]";	
	}

	public Dinheiro valor() {
		return valor;
	}
	
	public Mensagem mensagem(){
		return mensagem;
	}

	public void atualizarMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public ChaveAcesso chave() {
		return chave;
	}

	public void setChave(ChaveAcesso chaveAcesso) {
		this.chave = chaveAcesso;
	}

	public TipoEmissao tipoEmissao() {
		return tipoEmissao;
	}
}
