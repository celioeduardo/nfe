package com.hadrion.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
		
		return notaFiscalAplicacaoService.notasFicaisPendentesAutorizacaoResumo(empresa,filial,inicio,fim,usuario,notaFiscalId);
	}
	@RequestMapping("/notas_fiscais/pendentes_autorizacao")
	public List<NotaFiscalData> pendentes_autorizacao(HttpServletRequest req){
		return notaFiscalAplicacaoService.notasFicaisPendentesAutorizacao(); 
	}
	@RequestMapping("/notas_fiscais/configuracao")
	public String configuracao(HttpServletRequest req){
		return "[{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"REGISTRO AUTOMATICO\"},{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"CAFE\"},{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"CAFE TERCEIROS\"},{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"CAFE **ARAX\\u00C1**\"},{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"HORTI FRUTI\"},{\"NUM_CNPJ\":86675642000882,\"NOM_CURTO_FILIAL\":\"LAVADOR - COOPADAP\"},{\"NUM_CNPJ\":86675642000963,\"NOM_CURTO_FILIAL\":\"COOPADAP RURAL\"},{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"GRAOS\"},{\"NUM_CNPJ\":86675642000700,\"NOM_CURTO_FILIAL\":\"EERP-OUTRAS CULTURAS\"},{\"NUM_CNPJ\":86675642000700,\"NOM_CURTO_FILIAL\":\"ESTACAO EXPERIMENTAL\"},{\"NUM_CNPJ\":86675642000297,\"NOM_CURTO_FILIAL\":\"UND. ALHO\"},{\"NUM_CNPJ\":86675642000106,\"NOM_CURTO_FILIAL\":\"LOJA INSUMOS\"}]"; 
	}
	@RequestMapping("/notas_fiscais/combofilial")
	public String combofilial(
			@RequestParam(value="query",required=false,
			defaultValue="select NUM_CNPJ,cod_empresa||'/'||COD_FILIAL||' - '||nom_curto_filial nom_curto_filial from cad_filial where cod_empresa = 1 and cod_filial > 0")String query){
		return notaFiscalAplicacaoService.obterComboFilial(query); 
	}
	@RequestMapping(value="/notas_fiscais/danfe", method = RequestMethod.GET)
	@ResponseBody
	public FileSystemResource danfe(HttpServletResponse response) throws JRException{
        
		response.setContentType("application/pdf");
		
		JasperReport jasperReport;
		JasperPrint jasperPrint;

		File xmlFile = new File("src/test/resources/report/nfe.xml");  
        JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlFile,"/nfeProc/NFe/infNFe/det");
        
        jasperReport = JasperCompileManager.compileReport("src/test/resources/report/danfe.jrxml");
        jasperPrint = JasperFillManager.fillReport(jasperReport, null, xmlDataSource);  
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
        
        response.setHeader("Content-Disposition", "attachment; filename=src/test/resources/report/danfe.pdf"); 
        return new FileSystemResource("src/test/resources/report/danfe.pdf");

	}
	@RequestMapping(value = "/notas_fiscais/danfe2", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadStuff(@PathVariable int stuffId)
	                                                                  throws IOException {
	    File file = new File("src/test/resources/report/nfe.xml");

	    HttpHeaders respHeaders = new HttpHeaders();
	    respHeaders.setContentType(new MediaType("application", "pdf")); // ("application/pdf");
	    respHeaders.setContentLength(12345678);
	    respHeaders.setContentDispositionFormData("attachment", "danfe.pdf");

	    InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
	    return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
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
