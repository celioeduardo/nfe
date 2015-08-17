package com.hadrion.nfe.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;

import com.hadrion.comum.paginacao.Pagina;

public class Retorno {
	private List<? extends Object> rows;
	private boolean success = true;
	private int total;

	public Retorno(){
		super();
	}
	
	public Retorno(List<? extends Object> rows) {
		super();
		this.rows = rows;
	}
	
	public Retorno(Page<?> page){
		List<Object> result = new ArrayList<Object>(); 
		for (Object object : page)
			result.add(object);
		rows = result;
		total = page.getNumberOfElements();
	}
	
	public Retorno(Pagina<?> pagina){
		List<Object> result = new ArrayList<Object>(); 
		for (Object object : pagina)
			result.add(object);
		rows = result;
		total = pagina.getTotalDeElementos();
	}
	
//	public Retorno(Page<?> page) {
//		super();
//		this.rows = page.getPageItems();
//		this.total = page.getTotalRows();
//	}
	
	public Retorno(Object obj){
		super();
		this.rows = Collections.singletonList(obj);
	}
	
	public List<? extends Object> getRows() {
		return rows;
	}
	public void setRows(List<? extends Object> rows) {
		this.rows = rows;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
