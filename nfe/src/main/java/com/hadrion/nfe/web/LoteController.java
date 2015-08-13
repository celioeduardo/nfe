package com.hadrion.nfe.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.lote.LoteAplicacaoService;
import com.hadrion.nfe.aplicacao.lote.data.LoteData;
import com.hadrion.nfe.dominio.modelo.Ambiente;

@RestController
@RequestMapping("/lotes")
public class LoteController {

	@Autowired
	LoteAplicacaoService loteAplicacaoService; 
	
	@RequestMapping(value="/pendentes", method = RequestMethod.GET)
	public List<LoteData> pendentes(
			@RequestParam(value="ambiente")Ambiente ambiente,
			@RequestParam(value="empresa",required=false)Long empresa,
			@RequestParam(value="filial",required=false) String filial,
			@RequestParam(value="notista",required=false) String notista){
		
		return loteAplicacaoService.obterLotesPendentes(ambiente, filial, notista);
	}
	
	@RequestMapping(value="/processar_retorno", method = RequestMethod.POST)
	public String processar(
			@RequestBody List<String> ids){

		for (String loteId : ids) 
			loteAplicacaoService.processarRetorno(loteId);
		
		return "OK"; 
	}
	
}
