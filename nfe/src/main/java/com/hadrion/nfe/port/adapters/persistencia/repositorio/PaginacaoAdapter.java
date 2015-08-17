package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.hadrion.comum.paginacao.Paginacao;

public class PaginacaoAdapter implements Pageable {
	
	private Paginacao paginacao;
	private Sort sort;
	
	public PaginacaoAdapter(Paginacao paginacao){
		this.paginacao = paginacao;
	}
	public PaginacaoAdapter(Paginacao paginacao,Sort sort){
		this.paginacao = paginacao;
		this.sort = sort;
	}
	
	@Override
	public int getPageNumber() {
		return paginacao.pagina();
	}

	@Override
	public int getPageSize() {
		return size();
	}

	@Override
	public int getOffset() {
		return page() * size();
	}

	@Override
	public Sort getSort() {
		if (sort != null) 
			return sort;
		else
			return paginacao.ordenacao() != null ?
				OrdernacaoConverter.paraSort(paginacao.ordenacao()) : null;
	}

	@Override
	public Pageable next() {
		return new PageRequest(
				page() + 1, size(), sort());
	}

	@Override
	public Pageable previousOrFirst() {
		return hasPrevious() ? new PageRequest(
				page() - 1, size(), getSort()) : this;
	}

	@Override
	public Pageable first() {
		return new PageRequest(0, size(), sort());
	}
	
	private int page(){
		return paginacao.pagina();
	}
	private int size(){
		return paginacao.limite();
	}
	private Sort sort(){
		return getSort();
	}

	@Override
	public boolean hasPrevious() {
		return page() > 0;
	}

}
