package com.hadrion.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;

@RestController
@RequestMapping("/notas_fiscais")
public class NfeController {
	
	@Autowired
	NotaFiscalAplicacaoService notaFiscalAplicacaoService; 
	
	@RequestMapping("/pendentes_autorizacao")
	public List<NotaFiscalData> pendentes_autorizacao(HttpServletRequest req){
		List<NotaFiscalData> result = notaFiscalAplicacaoService.notasFicaisPendentesAutorizacao();
		
/*		for (NotaFiscalData nf : result) {
			if ("E".equals(nf.getTipo()))
				nf.setTipo("Entrada");
			else
				nf.setTipo("Saída");
		}*/
		
		return result;
	}

	
	@RequestMapping("/")
	public String index(HttpServletRequest req){
		//req.getRemoteUser();
		return "Uhuhuhuhuh - Spring Boot is ON id:"+req.getSession().getId();
	}
	
}
