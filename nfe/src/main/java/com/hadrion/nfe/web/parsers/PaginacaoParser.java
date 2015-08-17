package com.hadrion.nfe.web.parsers;

import javax.servlet.http.HttpServletRequest;

import com.hadrion.comum.paginacao.Ordenacao;
import com.hadrion.comum.paginacao.Paginacao;

public class PaginacaoParser {
	
	private String start;
	private String limit;
	private String page;
	private String sort;
	
	
	public PaginacaoParser(String start, String limit, String page,String sort){
		this.start = start;
		this.limit = limit;
		this.page = page;
		this.sort = sort;
	}

	public PaginacaoParser(String start, String limit, String page){
		this(start,limit,page,null);
	}
	
	public PaginacaoParser(HttpServletRequest request){
		this(
			request.getParameter("start"),
			request.getParameter("limit"),
			request.getParameter("page"),
			request.getParameter("sort"));
	}
	
	public Paginacao parse(){
		return new Paginacao(inicio(), limite(), pagina(), ordenacao());
	}
	
	private int inicio(){
		return Integer.valueOf(start);
	}
	private int pagina(){
		return ajustarPagina(Integer.valueOf(page));
	}
	
	private int limite(){
		return Integer.valueOf(limit);
	}
	
	private Ordenacao ordenacao(){
		return sort != null ? 
				OrdenacaoParser.parse(sort) : null;
	}
	
	private int ajustarPagina(int page){
		return page > 0 ? page - 1 : page;
	}
	
}
