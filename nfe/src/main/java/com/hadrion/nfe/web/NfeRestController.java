package com.hadrion.nfe.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;

@RestController
@RequestMapping("/notas_fiscais")
public class NfeRestController extends AbstractRestController {
	
	@Autowired
	private NotaFiscalAplicacaoService notaFiscalAplicacaoService;
	
	@RequestMapping(value="/{parametro}", method = RequestMethod.GET)
	public Object pendentesAutorizacaoResumo(
			@PathVariable("parametro") String parametro){
		
		List<NotaFiscalId> ids = Arrays.stream(StringUtils.split(parametro, ","))
				.map(NotaFiscalId::new)
				.collect(Collectors.toList()); 
		
		return ids.size() == 1 ?
				notaFiscalAplicacaoService.notaFiscalAutorizada(ids.get(0))
					.orElseThrow(RecursoNaoEncontradoException::new):
				notaFiscalAplicacaoService.notasFiscaisAutorizadas(ids);
	}
	
}
