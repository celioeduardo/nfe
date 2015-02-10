package com.hadrion.nfe.dominio.modelo.portal.autorizacao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;

public class RetornoAutorizacao {
	
	private ReciboLote recibo;
	private Mensagem erro;
	
	private Map<ChaveAcesso, String> erros; 
	
	public RetornoAutorizacao(ReciboLote recibo){
		this.setRecibo(recibo);
	}
	
	public RetornoAutorizacao(Mensagem erro){
		this.setErro(erro);
	}
	
	public RetornoAutorizacao() {
	}
	
	public void registrarErro(ChaveAcesso chaveAcesso, String msg){
		getErros().put(chaveAcesso, msg);
	}
	
	public Map<ChaveAcesso, String> erros(){
		return Collections.unmodifiableMap(getErros());
	}
	
	private Map<ChaveAcesso, String> getErros(){
		if (erros == null)
			erros = new HashMap<ChaveAcesso, String>();
		return erros;
	}
	
	public boolean sucesso(){
		return recibo != null && recibo.numero() != null;
	}
	
	public ReciboLote recibo(){
		return recibo;
	}
	
	public Mensagem erro(){
		return erro;
	}

	private void setRecibo(ReciboLote recibo){
		this.recibo = recibo;
	}
	
	private void setErro(Mensagem erro){
		this.erro = erro;
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			RetornoAutorizacao objetoTipado = (RetornoAutorizacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(recibo(),objetoTipado.recibo())
				.append(erro(),objetoTipado.erro())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(9845,19)
			.append(recibo())
			.append(erro())
			.toHashCode();
	}

	@Override
	public String toString() {
		return "RetornoAutorizacao [recibo=" + recibo()
				+",erro=" + erro()
				+ "]";
	}

	public boolean temErros() {
		return getErros().size() > 0;
	}
	
}
