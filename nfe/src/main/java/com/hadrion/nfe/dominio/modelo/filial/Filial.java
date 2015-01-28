package com.hadrion.nfe.dominio.modelo.filial;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioRegistro;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Contingencia;
import com.hadrion.nfe.tipos.Cnpj;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_FILIAL")
@Table(name="FILIAL")
public class Filial {
	
	@Embedded
	private FilialId filialId;
	
	@Column(name="NOME")
	private String nome;
	
	@Embedded
	private Cnpj cnpj;
	
	@Embedded
	private EmpresaId empresaId;
	
	@Enumerated(EnumType.STRING)
	private Ambiente ambiente;
	
	@Enumerated(EnumType.STRING)
	private ModoOperacao modoOperacao;
	
	@Embedded
	private Contingencia contingencia;
	
	@Column(name="apelido")
	private String apelido;
	
	@Column(name="UF")
	@Enumerated(EnumType.STRING)
	private Uf uf;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private Long id;

	public Filial(FilialId filialId, String nome, Cnpj cnpj,
			EmpresaId empresaId, Ambiente ambiente, String apelido) {
		this.filialId = filialId;
		this.nome = nome;
		this.cnpj = cnpj;
		this.empresaId = empresaId;
		this.ambiente = ambiente;
		this.modoOperacao = ModoOperacao.NORMAL;
		this.apelido = apelido;
	}
	
	public FilialId filialId(){
		return filialId;
	}
	
	public String nome(){
		return nome;
	}
	
	public Cnpj cnpj(){
		return cnpj;
	}
	
	public EmpresaId empresaId(){
		return empresaId;
	}

	public void renomear(String novoNome) {
		this.nome = novoNome;
	}
	
	public Ambiente ambiente(){
		return this.ambiente;
	}
	
	/**
	 * Somente para JPA
	 */
	@SuppressWarnings("unused")
	private Filial(){}

	public void operarEmProducao() {
		this.ambiente = Ambiente.PRODUCAO;
	}
	
	public void operarEmHomologacao() {
		this.ambiente = Ambiente.HOMOLOGACAO;
	}

	public ModoOperacao modoOperacao() {
		return modoOperacao;
	}

	public void operarEmFsDa(Contingencia contingencia) {
		if (contingencia == null)
			throw new RuntimeException("Informação de contingência é obrigatória");
		
		if (modoOperacao() == ModoOperacao.FS_DA
				&& contingencia.equals(contingencia())) return;
		
		this.setModoOperacao(ModoOperacao.FS_DA,contingencia);
	}
	
	public void operarEmSvc(Contingencia contingencia) {
		if (contingencia == null)
			throw new RuntimeException("Informação de contingência é obrigatória");
		
		if (modoOperacao() == ModoOperacao.SVC 
				&& contingencia.equals(contingencia())) return;
		
		this.setModoOperacao(ModoOperacao.SVC,contingencia);
	}
	
	public void operarEmModoNormal() {
		if (modoOperacao() == ModoOperacao.NORMAL) return;
		this.setModoOperacao(ModoOperacao.NORMAL,null);
	}
	
	private void setModoOperacao(ModoOperacao modoOperacao, Contingencia contingencia){
		this.modoOperacao = modoOperacao;
		this.contingencia = contingencia;
		
		DominioRegistro.eventoDominioPublicador().publicar(
				new ModoOperacaoAlterado(
						filialId().id(), 
						modoOperacao(), 
						contingencia != null ? contingencia.dataHora() : null, 
						contingencia != null ? contingencia.justificativa() : null));
	}

	public Contingencia contingencia() {
		return contingencia;
	}
	
	public String apelido(){
		return apelido;
	}
	
	public void alterarApelido(String novoApelido){
		this.apelido = novoApelido;
	}
	
	public Uf uf(){
		return this.uf;
	}
	
}
