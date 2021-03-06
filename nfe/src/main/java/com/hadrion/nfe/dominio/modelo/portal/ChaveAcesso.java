package com.hadrion.nfe.dominio.modelo.portal;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.util.GeradorDVModulo11;

@Embeddable
@Access(AccessType.FIELD)
public class ChaveAcesso {
	
	@Column(name="CHAVE")
	private String chave;

	public ChaveAcesso(String chave) {
		super();
		this.chave = chave;
	}
	
	public ChaveAcesso(Uf uf, Date data, Cnpj cnpj, Serie serie, 
			long numero, TipoEmissao tipoEmissao, int codigoNumerico) {
		
		chave = chaveComDigitoVerificador(
				uf.codigo() +
				formatarDataEmissao(data) +
				cnpj.fixo() +
				"55" +
				formatarSerie(serie) +
				formatarNumero(numero) +
				tipoEmissao.codigo() +
				formatarCodigoNumerico(codigoNumerico));
		
	}
	
	public String chave(){
		return chave;
	}

	public int codigo() {
		return Integer.parseInt(StringUtils.substring(chave,36, 43));
	}

	public int digitoVerificador() {
		return Integer.parseInt(StringUtils.substring(chave,43, 44));
	}

	public Uf uf() {
		return Uf.obterPeloCodigo(StringUtils.substring(chave,0, 2));
	}
	
	private String chaveComDigitoVerificador(String chaveParcial){
		int digitoVerificador = new GeradorDVModulo11(chaveParcial).gerar();
		return chaveParcial + digitoVerificador;
	}
	
	private String formatarDataEmissao(Date data){
		return new SimpleDateFormat("yyMM").format(data);
	}
	
	private String formatarSerie(Serie serie){
		return String.format("%03d", serie.numero());
	}
	
	private String formatarNumero(long numero){
		return String.format("%09d", numero);
	}
	
	private String formatarCodigoNumerico(long codigo){
		return String.format("%08d", codigo);
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;
	
		if (objeto != null && this.getClass() == objeto.getClass()) {
			ChaveAcesso objetoTipado = (ChaveAcesso) objeto;
			objetosIguais = this.chave().equals(objetoTipado.chave());
		} 
	
		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(1723,5155)
		.append(chave)
		.toHashCode();
	}

	@Override
	public String toString() {
		return chave; 	
	}

	public String anoMes() {
		return StringUtils.substring(chave,2, 6);
	}

	public Cnpj cnpj() {
		return new Cnpj(Long.parseLong(
				StringUtils.substring(chave,6, 20)));
	}

	public Modelo modelo() {
		return new Modelo(StringUtils.substring(chave,20, 22));
	}

	public Serie serie() {
		return new Serie(Long.parseLong(StringUtils.substring(chave,22, 25)));
	}
	
	public Long numero() {
		return Long.parseLong(StringUtils.substring(chave,25, 34));
	}

	public TipoEmissao tipoEmissao() {
		return TipoEmissao.obterPeloCodigo(
				Integer.parseInt(StringUtils.substring(chave,34, 35)));
	}

	public boolean igualChaveDocumento(ChaveAcesso chave) {
		return new EqualsBuilder()
			.append(cnpj(),chave.cnpj())
			.append(numero(),chave.numero())
			.append(serie(),chave.serie())
			.append(modelo(),chave.modelo())
			.isEquals(); 
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private ChaveAcesso(){}
	
}
