package com.hadrion.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

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

import com.hadrion.nfe.aplicacao.nf.CancelarNotaComando;
import com.hadrion.nfe.aplicacao.nf.EnviarEmailComando;
import com.hadrion.nfe.aplicacao.nf.EnviarNotasComando;
import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.aplicacao.nf.RegistrarCartaCorrecaoComando;
import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.util.xml.XmlUtil;

@RestController
@RequestMapping("/notas_fiscais")
public class NfeController {

	@Autowired
	NotaFiscalAplicacaoService notaFiscalAplicacaoService; 
	
	@RequestMapping(value="/pendentes_autorizacao_resumo", method = RequestMethod.GET)
	public List<NotaFiscalData> pendentesAutorizacaoResumo(
			@RequestParam(value="ambiente") Ambiente ambiente ,
			@RequestParam(value="empresa",required=false)Double empresa,
			@RequestParam(value="filial")String filial,
			@RequestParam(value="inicio",required=false)Date inicio,
			@RequestParam(value="fim",required=false)Date fim,
			@RequestParam(value="notista",required=false)String notista,
			@RequestParam(value="notafiscalId",required=false)String notaFiscalId){

		return notaFiscalAplicacaoService.notasFicaisPendentesAutorizacaoResumo(
				ambiente,empresa,filial,inicio,fim,notista,notaFiscalId);
		
	}
	
	@RequestMapping(value="/autorizadas_resumo", method = RequestMethod.GET)
	public List<NotaFiscalData> autorizadasResumo(
			@RequestParam(value="ambiente") Ambiente ambiente ,
			@RequestParam(value="empresa",required=false)Double empresa,
			@RequestParam(value="filial")String filial,
			@RequestParam(value="inicio",required=false)Date inicio,
			@RequestParam(value="fim",required=false)Date fim,
			@RequestParam(value="notista",required=false)String notista,
			@RequestParam(value="notafiscalid",required=false)String notaFiscalId,
			@RequestParam(value="nao_impressa",required=false)boolean naoImpressa){
		
		if (naoImpressa)
			return notaFiscalAplicacaoService.notasFicaisAutorizadasNaoImpressasResumo(
					ambiente,empresa,filial,inicio,fim,notista,notaFiscalId);
		else
			return notaFiscalAplicacaoService.notasFicaisAutorizadasResumo(
					ambiente,empresa,filial,inicio,fim,notista,notaFiscalId);
	}
	
	@RequestMapping(value="/canceladas_resumo", method = RequestMethod.GET)
	public List<NotaFiscalData> canceladasResumo(
			@RequestParam(value="ambiente") Ambiente ambiente ,
			@RequestParam(value="empresa",required=false)Double empresa,
			@RequestParam(value="filial")String filial,
			@RequestParam(value="inicio",required=false)Date inicio,
			@RequestParam(value="fim",required=false)Date fim,
			@RequestParam(value="notista",required=false)String notista,
			@RequestParam(value="notafiscalid",required=false)String notaFiscalId){
		
			return notaFiscalAplicacaoService.notasFicaisCanceladasResumo(
					ambiente,empresa,filial,inicio,fim,notista,notaFiscalId);
	}
	
	@RequestMapping(value = "/pre_visualizar_danfe", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> preVisualizarDanfe(
			@RequestParam(value="notaFiscalId")String notaFiscalId) throws IOException, JRException{		
		return notaFiscalAplicacaoService.preVisualizarDanfe(notaFiscalId);
	}	
	
	@RequestMapping(value = "/imprimir_danfe", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> imprimirDanfe(
			@RequestParam(value="notaFiscalId")String notaFiscalId) throws IOException, JRException{		
		return notaFiscalAplicacaoService.imprimirDanfe(notaFiscalId);
	}	

	@RequestMapping(value = "/enviar", method = RequestMethod.POST)
	@ResponseBody
	public String enviarNotas(
			@RequestBody List<EnviarNotasComando> comandos) throws IOException, JRException{		
		return notaFiscalAplicacaoService.enviarNotas(comandos.get(0));
	}	
	@RequestMapping(value = "/enviar_email", method = RequestMethod.GET)
	public String enviarEmail(
			@RequestParam String notaFiscalId) throws IOException, MessagingException, JRException{		
		return notaFiscalAplicacaoService.enviarEmail(new EnviarEmailComando(notaFiscalId));
	}	
	
	@RequestMapping(value = "/cancelar", method = RequestMethod.POST)
	@ResponseBody
	public String cancelar(
			@RequestBody CancelarNotaComando comando) throws IOException, JRException{		
		return notaFiscalAplicacaoService.cancelar(comando);
		//return "OK"; //Chamada fake
	}	
	@RequestMapping(value = "/registrar_cce", method = RequestMethod.POST)
	@ResponseBody
	public String registrarCartaCorrecao(
			@RequestBody RegistrarCartaCorrecaoComando comando){		
		notaFiscalAplicacaoService.registrarCartaCorrecao(comando);
		return "OK";
	}	
	@RequestMapping(value = "/xml_cce", method = RequestMethod.GET)
	@ResponseBody
	public String xmlCce(
			@RequestParam String notaFiscalId){		
		return XmlUtil.xmlParaString(notaFiscalAplicacaoService.xmlCce(notaFiscalId));
	}	
	@RequestMapping(value = "/imprimir_cce", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> imprimirCce(
			@RequestParam(value="notaFiscalId")String notaFiscalId) throws IOException, JRException{		
		return notaFiscalAplicacaoService.imprimirCce(notaFiscalId);
	}	
	@RequestMapping(value = "/xml_nfe", method = RequestMethod.GET,produces="application/xml;charset=utf-8")
	@ResponseBody
	public ResponseEntity<InputStreamResource>  xmlNfe(
			@RequestParam String notaFiscalId) throws JRException, TransformerConfigurationException, TransformerException, TransformerFactoryConfigurationError{		
		return notaFiscalAplicacaoService.xmlNfe(notaFiscalId);
	}	
}
