package com.hadrion.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.notista.NotistaAplicacaoService;
import com.hadrion.nfe.aplicacao.notista.data.NotistaData;

@RestController
@RequestMapping("/notista")
public class NotistaController {

	@Autowired
	private NotistaAplicacaoService notistaAplicacaoService;
	
	@RequestMapping("/notistas")
	public List<NotistaData> obterTodos(HttpServletRequest req){
		return notistaAplicacaoService.obterTodos(); 
	}
		
}
