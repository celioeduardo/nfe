package com.hadrion.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.filial.FilialAplicacaoService;
import com.hadrion.nfe.aplicacao.filial.data.FilialData;

@RestController
@RequestMapping("/filial")
public class FilialController {

	@Autowired
	FilialAplicacaoService filialAplicacaoService;

	
	@RequestMapping("/obter")
	public List<FilialData> obter(HttpServletRequest req){
		return filialAplicacaoService.obterTodas(); 
	}

}
