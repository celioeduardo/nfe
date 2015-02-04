package com.hadrion.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.inutilizacao.InutilizacaoAplicacaoService;
import com.hadrion.nfe.aplicacao.inutilizacao.InutilizarComando;
import com.hadrion.nfe.aplicacao.inutilizacao.data.InutilizacaoData;

@RestController
@RequestMapping("/inutilizacao")
public class InutilizacaoController {
	
	@Autowired
	private InutilizacaoAplicacaoService inutilizacaoAplicacaoService;
	
	@RequestMapping(value="/inutilizar", method = RequestMethod.POST)
	public InutilizacaoData inutilizar(@RequestBody InutilizarComando comando){
		inutilizacaoAplicacaoService.inutilizar(comando);
		return inutilizacaoAplicacaoService.obter(comando.getInutilizacaoId());
	}
	
}
