package com.hadrion.nfe.dominio.modelo.nf.informacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
@Access(AccessType.FIELD)
public class Informacao {
	
	@Column(name="TEXTO",length=400)
	private String texto;
	
	@OneToMany(orphanRemoval=true, cascade=CascadeType.ALL)
	@JoinTable(name="OBSERVACOES",
	    joinColumns=@JoinColumn(name="ID_NF"),
	    inverseJoinColumns=@JoinColumn(name="ID_OBSERVACAO"))
	private List<Observacao> observacoes;
	
	public Informacao(){
		
	}
	
	public Informacao(String texto, Observacao ... observacoes) {
		super();
		this.texto = texto;
		if (observacoes != null)  
			getObservacoes().addAll(Arrays.asList(observacoes));
	}
	
	
	public String texto() {
		return texto;
	}

	public List<Observacao> observacoes() {
		return getObservacoes();
	}
	
	private List<Observacao> getObservacoes() {
		if (observacoes == null)
			observacoes = new ArrayList<Observacao>();
		return observacoes;
	}

	public Informacao mesclar(Informacao outra){
		if (outra == null) {
			this.limpar();
			return this;
		}
		if (equals(outra)) return this;
		this.texto = outra.texto;
		this.getObservacoes().clear();
		this.getObservacoes().addAll(Arrays.asList(clonarObservacoes(outra)));
		return this;
//		return new Informacao(
//				outra.texto,
//				clonarObservacoes(outra));
		
	}
	
	private Observacao[] clonarObservacoes(Informacao inf){
		List<Observacao> result = new ArrayList<Observacao>(getObservacoes().size());
		for (Observacao observacao : inf.getObservacoes()) 
			result.add(observacao.clonar());
		return result.toArray(new Observacao[result.size()]);
	}
	
	@Override
	public boolean equals(Object objeto) {
		boolean objetosIguais = false;

		if (objeto != null && this.getClass() == objeto.getClass()) {
			Informacao objetoTipado = (Informacao) objeto;
			objetosIguais = new EqualsBuilder()
				.append(texto(), objetoTipado.texto())
				.append(observacoes(), objetoTipado.observacoes())
				.isEquals();
		}

		return objetosIguais;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(477,371)
		.append(texto())
		.append(observacoes())
		.toHashCode();
	}
	
	@Override
	public String toString() {
		return "Informacao [texto=" + texto()
				+ ",observacoes=" + observacoes()
				+ "]";	
	}

	public void limpar() {
		texto = null;
		getObservacoes().clear();
	}

	public Informacao clonar() {
		return new Informacao(
				texto, 
				getObservacoes().toArray(new Observacao[getObservacoes().size()]));
	}
}
