package com.hadrion.nfe.port.adapters.portal.evento;

import static org.apache.commons.lang.StringUtils.leftPad;

import java.util.Date;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.tipos.Cnpj;

public abstract class Evento {
	
	protected long tipo;

	protected String descricao;

	protected Uf uf;
	
	protected Ambiente ambiente;
	protected Cnpj cnpjAutor;
	protected ChaveAcesso chaveAcesso;
	protected Date dataHora;
	protected long sequencia;
	protected String versao;

	protected Evento(long tipo, String descricao,
			Uf uf, Ambiente ambiente, Cnpj cnpjAutor,
			ChaveAcesso chaveAcesso, Date dataHora, long sequencia) {
		super();
		this.tipo = tipo;
		this.descricao = descricao;
		this.uf = uf;
		this.ambiente = ambiente;
		this.cnpjAutor = cnpjAutor;
		this.chaveAcesso = chaveAcesso;
		this.dataHora = dataHora;
		this.sequencia = sequencia;
		this.versao = "1.00";
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

	public String versao(){
		return versao;
	}
	
	public Uf uf(){
		return uf;
	}
}
