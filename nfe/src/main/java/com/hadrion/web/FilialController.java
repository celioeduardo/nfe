package com.hadrion.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.empresa.EmpresaAplicacaoService;
import com.hadrion.nfe.aplicacao.empresa.EmpresaFilialData;
import com.hadrion.nfe.aplicacao.empresa.data.EmpresaData;
import com.hadrion.nfe.aplicacao.filial.AlterarModoOperacaoComando;
import com.hadrion.nfe.aplicacao.filial.FilialAplicacaoService;
import com.hadrion.nfe.aplicacao.filial.data.FilialData;

@RestController
@RequestMapping("/filial")
public class FilialController {

	@Autowired
	private FilialAplicacaoService filialAplicacaoService;
	
	@Autowired
	private EmpresaAplicacaoService empresaAplicacaoService;

	
	@RequestMapping("/obter")
	public List<FilialData> obter(HttpServletRequest req){
		return filialAplicacaoService.obterTodas(); 
	}
	
	@RequestMapping(value="/obter",params={"id"})
	public FilialData obter(HttpServletRequest req,
			@RequestParam String id){
		return filialAplicacaoService.obterPeloId(id); 
	}
	
	@RequestMapping("/empresa_filial")
	public EmpresaFilialData obterEmpresas(){
		return new EmpresaFilialData();
	}

	@RequestMapping(value="/empresa_filial",params={"node"})
	public List<EmpresaFilialData> obterFiliais(@RequestParam("node") String id){
		List<EmpresaFilialData> result = new ArrayList<EmpresaFilialData>();

		if ("root".equals(id)){
			List<EmpresaData> empresas = empresaAplicacaoService.obterTodas();
			
			for (EmpresaData data : empresas) {
				EmpresaFilialData empresa = new EmpresaFilialData(
						data.getEmpresaId(),
						data.getNome(), 
						new ArrayList<EmpresaFilialData>());
				empresa.setExpanded(true);
				result.add(empresa);
			}

		} else {
			List<FilialData> filiais = filialAplicacaoService.filiaisDaEmpresa(id);
			
			for (FilialData data : filiais) {
				EmpresaFilialData filial = new EmpresaFilialData(
						data.getFilialId(),
						data.getNome(), 
						new ArrayList<EmpresaFilialData>());
				filial.setLeaf(true);				
				result.add(filial);
			}
		
		}
		return result;
	}
	
	@RequestMapping(value="/alterar_modo_operacao", method = RequestMethod.POST)
	public String alterarModoOperacao(@RequestBody AlterarModoOperacaoComando comando){
		filialAplicacaoService.alterarModoOperacao(comando);
		return comando.getModoOperacao().toString();
	}
}
