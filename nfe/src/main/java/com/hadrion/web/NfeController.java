package com.hadrion.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hadrion.nfe.aplicacao.nf.EnviarNotasComando;
import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;
import com.hadrion.nfe.dominio.modelo.Ambiente;

@RestController
@RequestMapping("/notas_fiscais")
public class NfeController {

	@Autowired
	NotaFiscalAplicacaoService notaFiscalAplicacaoService; 
	
	@RequestMapping(value="/pendentes_autorizacao_resumo", method = RequestMethod.GET)
	public List<NotaFiscalData> pendentesAutorizacaoResumo(
			@RequestParam(value="ambiente",required=false)Ambiente ambiente ,
			@RequestParam(value="empresa",required=false)Double empresa,
			@RequestParam(value="filial",required=false)Double filial,
			@RequestParam(value="inicio",required=false)Date inicio,
			@RequestParam(value="fim",required=false)Date fim,
			@RequestParam(value="usuario",required=false)String usuario,
			@RequestParam(value="notafiscalid",required=false)String notaFiscalId){
		empresa = Double.parseDouble("86675642000106");
		filial = Double.parseDouble("86675642000106");
		
		//TODO passar ambiente na chamada da IU
		ambiente = Ambiente.HOMOLOGACAO;
		
		return notaFiscalAplicacaoService.notasFicaisPendentesAutorizacaoResumo(ambiente,empresa,filial,inicio,fim,usuario,notaFiscalId);
	}
	
	@RequestMapping(value="/autorizadas_resumo", method = RequestMethod.GET)
	public List<NotaFiscalData> autorizadasResumo(
			@RequestParam(value="ambiente",required=false)Ambiente ambiente ,
			@RequestParam(value="empresa",required=false)Double empresa,
			@RequestParam(value="filial",required=false)Double filial,
			@RequestParam(value="inicio",required=false)Date inicio,
			@RequestParam(value="fim",required=false)Date fim,
			@RequestParam(value="usuario",required=false)String usuario,
			@RequestParam(value="notafiscalid",required=false)String notaFiscalId,
			@RequestParam(value="nao_impressa",required=false)boolean naoImpressa){
		
		empresa = Double.parseDouble("86675642000106");
		filial = Double.parseDouble("86675642000106");
		
		//TODO passar ambiente na chamada da IU
		ambiente = Ambiente.HOMOLOGACAO;
		
		if (naoImpressa)
			return notaFiscalAplicacaoService.notasFicaisAutorizadasNaoImpressasResumo(ambiente,empresa,filial,inicio,fim,usuario,notaFiscalId);
		else
			return notaFiscalAplicacaoService.notasFicaisAutorizadasResumo(ambiente,empresa,filial,inicio,fim,usuario,notaFiscalId);
	}
	
	@RequestMapping(value="/autorizadas_nao_impressas_resumo", method = RequestMethod.GET)
	public List<NotaFiscalData> autorizadasNaoImpressasResumo(
			@RequestParam(value="ambiente",required=false)Ambiente ambiente ,
			@RequestParam(value="empresa",required=false)Double empresa,
			@RequestParam(value="filial",required=false)Double filial,
			@RequestParam(value="inicio",required=false)Date inicio,
			@RequestParam(value="fim",required=false)Date fim,
			@RequestParam(value="usuario",required=false)String usuario,
			@RequestParam(value="notafiscalid",required=false)String notaFiscalId){
		empresa = Double.parseDouble("86675642000106");
		filial = Double.parseDouble("86675642000106");
		
		//TODO passar ambiente na chamada da IU
		ambiente = Ambiente.HOMOLOGACAO;
		
		return notaFiscalAplicacaoService.notasFicaisAutorizadasNaoImpressasResumo(ambiente,empresa,filial,inicio,fim,usuario,notaFiscalId);
	}
	
	@RequestMapping("/pendentes_autorizacao")
	public List<NotaFiscalData> pendentesAutorizacao(HttpServletRequest req){
		//TODO parametrizar Ambiente
		return notaFiscalAplicacaoService.notasFicaisPendentesAutorizacao(Ambiente.HOMOLOGACAO); 
	}
	@RequestMapping("/configuracao")
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
	
	@RequestMapping(value = "/pre_visualizar_danfe", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> preVisualizarDanfe(
			@RequestParam(value="notafiscalid")String notaFiscalId) throws IOException, JRException{		
		return notaFiscalAplicacaoService.preVisualizarDanfe(notaFiscalId);
	}	
	
	@RequestMapping(value = "/imprimir_danfe", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> imprimirDanfe(
			@RequestParam(value="notafiscalid")String notaFiscalId) throws IOException, JRException{		
		return notaFiscalAplicacaoService.imprimirDanfe(notaFiscalId);
	}	
	
	@RequestMapping(value = "/enviar", method = RequestMethod.POST)
	@ResponseBody
	public String enviarNotas(
			@RequestBody List<EnviarNotasComando> comandos) throws IOException, JRException{		
		return notaFiscalAplicacaoService.enviarNotas(comandos.get(0));
	}	
}
