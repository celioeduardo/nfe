package com.hadrion.nfe.web.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.hadrion.comum.paginacao.Ordenacao;
import com.hadrion.comum.paginacao.Ordenacao.Direcao;
import com.hadrion.comum.paginacao.Ordenacao.Ordem;
import com.jayway.jsonpath.JsonPath;

class OrdenacaoParser {
	private String json;
	private Ordenacao ordenacao;
	
	public static Ordenacao parse(String json){
		return new OrdenacaoParser(json).parse();
	}
	
	public OrdenacaoParser(String json){
		this.json = json;
	} 

	public Ordenacao parse(){
		if(StringUtils.isEmpty(json)) return null;
		List<Ordem> ordens = new ArrayList<Ordenacao.Ordem>();
		for (Map<String, String> map : parseJson()) 
			ordens.add(criar(map.get("property"),map.get("direction")));
		ordenacao = new Ordenacao(ordens);
		return ordenacao;
	}

	private List<Map<String, String>> parseJson() {
		List<Map<String, String>> lista = JsonPath.read(json, "$.[*]");
		return lista;
	}
	
	private Ordem criar(String propriedade, String direcao){
		return new Ordem(propriedade, converterParaDirecao(direcao));
	}
	
	private Direcao converterParaDirecao(String arg){
		return Direcao.valueOf(arg);
	}
	
}
