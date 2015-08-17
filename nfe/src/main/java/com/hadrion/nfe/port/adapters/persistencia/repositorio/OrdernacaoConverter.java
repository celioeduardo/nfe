package com.hadrion.nfe.port.adapters.persistencia.repositorio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.hadrion.comum.paginacao.Ordenacao;
import com.hadrion.comum.paginacao.Ordenacao.Direcao;
import com.hadrion.comum.paginacao.Ordenacao.Ordem;

public class OrdernacaoConverter {
	
	private Ordenacao ordenacao;
	
	private OrdernacaoConverter(Ordenacao arg){
		this.ordenacao = arg;
	}
	
	public static Sort paraSort(Ordenacao arg){
		if (arg == null) return null;
		return new OrdernacaoConverter(arg).converter();
	}
	
	private Sort converter(){
		List<Order> orders = new ArrayList<Sort.Order>();
		
		for (Ordem ordem : ordenacao) 
			orders.add(converterParaOrder(ordem));
		
		return orders.size() > 0 ? new Sort(orders) : null;
	}
	
	private Order converterParaOrder(Ordem ordem){
		return new Order(
			ordem.direcao() == Direcao.ASC ? Direction.ASC : Direction.DESC,
			ordem.propriedade());
	}
	

}
