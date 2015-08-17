package com.hadrion.comum.paginacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class PaginaList<T> implements Pagina<T> {

	private int totalPaginas;
	private int totalElementos;
	private int numeroElementos;

	private List<T> items;

	public PaginaList(List<T> lista, int totalPaginas, int totalElementos,
			int numeroElementos) {
		items = new ArrayList<T>(lista);
		this.totalPaginas = totalPaginas;
		this.totalElementos = totalElementos;
		this.numeroElementos = numeroElementos;
	}
	
	public PaginaList(List<T> lista) {
		this(lista,1,lista.size(),lista.size());
	}

	public PaginaList(Collection<T> lista) {
		this(new ArrayList<T>(lista));
	}

	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}

	@Override
	public int getTotalDePaginas() {
		return totalPaginas;
	}

	@Override
	public int getTotalDeElementos() {
		return totalElementos;
	}

	@Override
	public int getNumeroDeElementos() {
		return numeroElementos;
	}

	@Override
	public List<T> getConteudo() {
		return items;
	}

	@Override
	public Stream<T> stream() {
		return items.stream();
	}
}
