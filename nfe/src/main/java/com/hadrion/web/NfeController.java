package com.hadrion.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;

@RestController
@RequestMapping("/")
public class NfeController {

	@Autowired
	NotaFiscalAplicacaoService notaFiscalAplicacaoService; 
	
	@RequestMapping(value="/notas_fiscais/pendentes_autorizacao_resumo", method = RequestMethod.GET)
	public List<NotaFiscalData> pendentes_autorizacao_resumo(
			@RequestParam(value="empresa",required=false)Double empresa,
			@RequestParam(value="filial",required=false)Double filial,
			@RequestParam(value="inicio",required=false)Date inicio,
			@RequestParam(value="fim",required=false)Date fim,
			@RequestParam(value="usuario",required=false)String usuario,
			@RequestParam(value="notafiscalid",required=false)String notaFiscalId){
		empresa = Double.parseDouble("86675642000106");
		filial = Double.parseDouble("86675642000106");
		return notaFiscalAplicacaoService.notasFicaisPendentesAutorizacaoResumo(empresa,filial,inicio,fim,usuario,notaFiscalId);
	}
	@RequestMapping("/notas_fiscais/pendentes_autorizacao")
	public List<NotaFiscalData> pendentes_autorizacao(HttpServletRequest req){
		return notaFiscalAplicacaoService.notasFicaisPendentesAutorizacao(); 
	}
	@RequestMapping("/notas_fiscais/configuracao")
	public String configuracao(HttpServletRequest req){
		return "[{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"REGISTRO AUTOMATICO\"},"
				+ "{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"CAFE\"},"
				+ "{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"CAFE TERCEIROS\"},"
				+ "{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"CAFE **ARAX\\u00C1**\"},"
				+ "{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"HORTI FRUTI\"},"
				+ "{\"NUM_CNPJ\":86675642000882,\"NOM_CURTO_FILIAL\":\"LAVADOR - COOPADAP\"},"
				+ "{\"NUM_CNPJ\":86675642000963,\"NOM_CURTO_FILIAL\":\"COOPADAP RURAL\"},"
				+ "{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"GRAOS\"},"
				+ "{\"NUM_CNPJ\":86675642000700,\"NOM_CURTO_FILIAL\":\"EERP-OUTRAS CULTURAS\"},"
				+ "{\"NUM_CNPJ\":86675642000700,\"NOM_CURTO_FILIAL\":\"ESTACAO EXPERIMENTAL\"},"
				+ "{\"NUM_CNPJ\":86675642000297,\"NOM_CURTO_FILIAL\":\"UND. ALHO\"},"
				+ "{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"LOJA INSUMOS\"}]"; 
	}
	
//	@RequestMapping("/notas_fiscais/combofilial")
//	public String combofilial(
//			@RequestParam(value="query",required=false,
//			defaultValue="select NUM_CNPJ,"
//					+ "cod_empresa||'/'||COD_FILIAL||' - '||nom_curto_filial nom_curto_filial "
//					+ "from cad_filial where cod_empresa = 1 and cod_filial > 0")String query){
//		return notaFiscalAplicacaoService.obterComboFilial(query); 
//	}

	@RequestMapping(value = "/notas_fiscais/danfe", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> danfe(
			@RequestParam(value="notafiscalid")String notaFiscalId) throws IOException, JRException{		
		return notaFiscalAplicacaoService.obterDanfe(notaFiscalId);
	}	
	@RequestMapping("/barra")
	public String index(HttpServletRequest req){
		return "Uhuhuhuhuh - Spring Boot is ON id:"+req.getSession().getId();
	}
	@RequestMapping("/home")
	public String home(HttpServletRequest req){
		return "home";
	}
}
