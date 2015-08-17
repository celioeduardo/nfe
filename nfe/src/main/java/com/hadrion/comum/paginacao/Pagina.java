package com.hadrion.comum.paginacao;

import java.util.List;
import java.util.stream.Stream;


public interface Pagina<T> extends Iterable<T>{

	public int getTotalDePaginas();
	public int getTotalDeElementos();
	public int getNumeroDeElementos();
	public List<T> getConteudo();
	public Stream<T> stream();
	
	
}