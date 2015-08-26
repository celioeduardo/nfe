package com.hadrion.nfe.dominio.modelo.nf.importacao;

import static com.hadrion.comum.Afirmacao.assertArgumentoNaoNulo;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;


public class Importacao {
	
	private int numero;
	private Date emissao;
	private String localDesembarque;
	private Uf ufDesembarque;
	private Date dataDesembarque;
	private ViaTransporte viaTransporte;
	private String codigoExportador;
	private Intermediacao intermediacao;
	private Optional<Dinheiro> valorArfmm;
	private Optional<Cnpj> cnpjTerceiro;
	private Optional<Uf> ufTerceiro;
	private Adicao adicao;
	public Importacao(int numero,Date data,String localDesembarque,Uf ufDesembarque, 
			Date dataDesembarque,ViaTransporte viaTransporte,String codigoExportador,
			Dinheiro valorArfmm,Intermediacao intermediacao,Cnpj cnpjTerceiro,Uf ufTerceiro,
			Adicao adicao) {
		setNumero(numero);
		setEmissao(data);
		setLocalDesembarque(localDesembarque);
		setUfDesembarque(ufDesembarque);
		setDataDesembarque(dataDesembarque);
		setViaTransporte(viaTransporte);
		setCodigoExportador(codigoExportador);
		setIntermediacao(intermediacao);
		setValorArfmm(valorArfmm);
		setCnpjTerceiro(cnpjTerceiro);
		setUfTerceiro(ufTerceiro);
		setAdicao(adicao);
	}
	
	public int numero() {
		return this.numero;
	}	
	public Date emissao() {
		return emissao;
	}	
	public String localDesembarque() {
		return localDesembarque;
	}
	public Uf ufDesembarque() {
		return ufDesembarque;
	}
	public Date dataDesembarque() {
		return dataDesembarque;
	}
	public ViaTransporte viaTransporte() {
		return viaTransporte;
	}
	public Intermediacao intermediacao() {
		return intermediacao;
	}
	public Optional<Dinheiro> valorArfmm() {
		return this.valorArfmm;
	}
	public Optional<Cnpj> cnpjTerceiro() {
		return cnpjTerceiro;
	}
	public Optional<Uf> ufTerceiro() {
		return ufTerceiro;
	}
	public String codigoExportador() {
		return codigoExportador;
	}
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Importacao objetoTipado = (Importacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(numero,objetoTipado.numero)
				.append(emissao,objetoTipado.emissao)
				.append(localDesembarque,objetoTipado.localDesembarque)
				.append(ufDesembarque,objetoTipado.ufDesembarque)
				.append(dataDesembarque,objetoTipado.dataDesembarque)
				.append(viaTransporte,objetoTipado.viaTransporte)
				.append(codigoExportador,objetoTipado.codigoExportador)
				.append(intermediacao,objetoTipado.intermediacao)
				.append(valorArfmm,objetoTipado.valorArfmm)
				.append(cnpjTerceiro,objetoTipado.cnpjTerceiro)
				.append(ufTerceiro,objetoTipado.ufTerceiro)
				.append(adicao,objetoTipado.adicao)
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1327,171)
			.append(numero)
			.append(emissao)
			.append(localDesembarque)
			.append(ufDesembarque)
			.append(dataDesembarque)
			.append(viaTransporte)
			.append(codigoExportador)
			.append(intermediacao)
			.append(valorArfmm)
			.append(cnpjTerceiro)
			.append(ufTerceiro)
			.append(adicao)
			.toHashCode();
	}

	@Override
	public String toString() {
		return "Importacao [numero=" + numero
				+ ",emissao="+ emissao
				+ ",localDesembarque="+ localDesembarque
				+ ",ufDesembarque="+ ufDesembarque
				+ ",dataDesembarque="+ dataDesembarque
				+ ",viaTransporte="+ viaTransporte
				+ ",codigoExportador="+ codigoExportador
				+ ",intermediacao="+ intermediacao
				+ ",valorArfmm="+ valorArfmm
				+ ",cnpjTerceiro="+ cnpjTerceiro
				+ ",ufTerceiro="+ ufTerceiro
				+ ",adicao="+ adicao
				+ "]";
	}	
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Importacao(){}
	
	private void setEmissao(Date emissao) {
		assertArgumentoNaoNulo(emissao, "Data de emissão é obrigatória.");
		this.emissao = emissao;
	}

	private void setLocalDesembarque(String localDesembarque) {
		assertArgumentoNaoNulo(localDesembarque, "Local de Desembarque é obrigatório.");
		this.localDesembarque = localDesembarque;
	}

	private void setUfDesembarque(Uf ufDesembarque) {
		assertArgumentoNaoNulo(ufDesembarque, "UF Data de Desembarque é obrigatória.");
		this.ufDesembarque = ufDesembarque;
	}

	private void setDataDesembarque(Date dataDesembarque) {
		assertArgumentoNaoNulo(dataDesembarque, "Data de Desembarque é obrigatória.");
		this.dataDesembarque = dataDesembarque;
	}

	private void setViaTransporte(ViaTransporte viaTransporte) {
		assertArgumentoNaoNulo(viaTransporte, "Via de Ttransporte Internacional é obrigatória.");
		this.viaTransporte = viaTransporte;
	}

	private void setCodigoExportador(String codigoExportador) {
		assertArgumentoNaoNulo(codigoExportador, "Código do Exportador é obrigatório.");
		this.codigoExportador = codigoExportador;
	}

	private void setAdicao(Adicao adicao) {
		assertArgumentoNaoNulo(adicao, "Adições são obrigatórias.");
		this.adicao = adicao;
	}
	
	private void setIntermediacao(Intermediacao intermediacao) {
		assertArgumentoNaoNulo(intermediacao, "Tipo de Intermediação é obrigatório.");
		this.intermediacao = intermediacao;
	}
	
	private void setNumero(int numero){
		if (numero==0) 
			throw new IllegalArgumentException("Número da D.I. é obrigatório");
		this.numero=numero;
	}

	private void setValorArfmm(Dinheiro valorArfmm) {
		this.valorArfmm = Optional.ofNullable(valorArfmm);
	}
	
	private void setCnpjTerceiro(Cnpj cnpjTerceiro) {
		this.cnpjTerceiro = Optional.ofNullable(cnpjTerceiro);
	}
	
	private void setUfTerceiro(Uf ufTerceiro) {
		this.ufTerceiro = Optional.ofNullable(ufTerceiro);
	}
		
}
