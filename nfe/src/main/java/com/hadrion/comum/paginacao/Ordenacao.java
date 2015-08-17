package com.hadrion.comum.paginacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Ordenacao implements Iterable<com.hadrion.comum.paginacao.Ordenacao.Ordem>{
	
	private List<Ordem> ordens = new ArrayList<Ordenacao.Ordem>();
	
	public Ordenacao(List<Ordem> ordens){
		this.ordens = ordens;
	}
	
	public Ordenacao(Ordem ... ordens){
		this(Arrays.asList(ordens));
	}
	
	public Ordem getOrdemPara(String propriedade){
		for (Ordem ordem : ordens) {
			if (ordem.propriedade().equals(propriedade))
				return ordem;
		}
		return null;
	}
	
	public Direcao getDirecaoPara(String propriedade){
		return getOrdemPara(propriedade) != null ? 
			getOrdemPara(propriedade).direcao():null;
	}
	
//	public static Ordenacao criar(){
//		return new Ordenacao();
//	}
	
//	public Ordenacao asc(String propriedade){
//		ordens.add(new Ordem(propriedade, Direcao.ASC));
//		return this;
//	}
//	
//	public Ordenacao desc(String propriedade){
//		ordens.add(new Ordem(propriedade, Direcao.DESC));
//		return this;
//	}
	
	public static enum Direcao {
		ASC,DESC
	}
	
	public static class Ordem{
		private final String propriedade;
		private final Direcao direcao;
		
		public Ordem(String propriedade, Direcao direcao) {
			super();
			this.propriedade = propriedade;
			this.direcao = direcao;
		}
		
		public Ordem(String propriedade, String direcao) {
			this(propriedade,Direcao.valueOf(direcao));
		}
		
		public String propriedade(){
			return propriedade;
		}
		public Direcao direcao(){
			return direcao;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (obj == this) return true;
			if (obj.getClass() != getClass()) return false;
			Ordem outro = (Ordem) obj;
			return new EqualsBuilder()
				.append(propriedade, outro.propriedade)
				.append(direcao, outro.direcao)
				.isEquals();
		}
		@Override
		public int hashCode() {
			return new HashCodeBuilder()
				.append(propriedade)
				.append(direcao)
				.toHashCode();
		}
	}

	@Override
	public Iterator<Ordem> iterator() {
		return this.ordens.iterator();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (obj.getClass() != getClass()) return false;
		Ordenacao outro = (Ordenacao) obj;
		return new EqualsBuilder()
			.append(ordens, outro.ordens).isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
			.append(ordens)
			.toHashCode();
	}
	
}
