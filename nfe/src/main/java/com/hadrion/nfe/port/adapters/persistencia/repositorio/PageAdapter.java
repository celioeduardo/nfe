package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;

import com.hadrion.comum.paginacao.Pagina;

public class PageAdapter<T> implements Pagina<T>{
	
	private Page<T> page;
	
	public PageAdapter(Page<T> page){
		this.page = page;
	}
	
	@Override
	public Iterator<T> iterator() {
		return page.iterator();
	}

	@Override
	public int getNumeroDeElementos() {
		return page.getNumberOfElements();
	}

	@Override
	public int getTotalDePaginas() {
		return page.getTotalPages();
	}

	@Override
	public int getTotalDeElementos() {
		return (int) page.getTotalElements();
	}

	@Override
	public List<T> getConteudo() {
		return page.getContent();
	}

	@Override
	public Stream<T> stream() {
		return page.getContent().stream();
	}
	
}
