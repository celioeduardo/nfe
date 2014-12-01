package com.hadrion.nfe.aplicacao.nf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.aplicacao.nf.data.NotaFiscalData;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalSerializador;

@Service
@Transactional
public class NotaFiscalAplicacaoService {
	
	@Autowired
	private NotaFiscalRepositorio repositorio;
	
	public List<NotaFiscalData> notasFicaisPendentesAutorizacaoResumo(Double empresa,Double filial,
			Date inicio,Date fim,String usuario,String notaFiscalId){
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		NotaFiscalId notaFiscalIdFiltro = null;
		if (notaFiscalId!=null)
			notaFiscalIdFiltro=new NotaFiscalId(notaFiscalId);
		for (DescritorNotaFiscal nf : repositorio.notasPendentesAutorizacaoResumo(empresa,filial,inicio,fim,usuario,notaFiscalIdFiltro)) {
			result.add(new NotaFiscalData(nf.notaFiscalId().id(),
					nf.numero(),
					String.valueOf(nf.serie().numero()),
					nf.emissao(),
					nf.valor().valor(),
					nf.publicoTipo(),
					nf.publicoCodigo(),
					nf.publicoNome(),
					nf.tipo()));
		}
		
		return result;
	}
	public List<NotaFiscalData> notasFicaisPendentesAutorizacao(){
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		
		for (NotaFiscal nf : repositorio.notasPendentesAutorizacao(null)) {
			result.add(new NotaFiscalData(nf.notaFiscalId().id(),
					nf.numero(),
					String.valueOf(nf.serie().numero()),
					nf.emissao(),
					nf.total().valor(),
					"C",
					1L,
					nf.destinatario().razaoSocial(),
					nf.tipoOperacao().toString()));
		}
		
		return result;
	}
	public String obterComboFilial(String query){
		return repositorio.queryToJson(query);
	}
	public ResponseEntity<InputStreamResource> obterDanfe(String notaFiscalId) throws IOException,JRException{
		
		JasperReport jasperReport;
		JasperPrint jasperPrint;

		NotaFiscalSerializador serializador = new NotaFiscalSerializador();
		InputStream xmlFile = IOUtils.toInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
    			"<nfeProc>\r\n" +
				serializador.serializar(repositorio.notaFiscalPeloId(new NotaFiscalId(notaFiscalId))) +
    			"</nfeProc>", "UTF-8");
		
		JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlFile,"/nfeProc/NFe/infNFe/det");		
		jasperReport = JasperCompileManager.compileReport("src/test/resources/report/danfe.jrxml");
		jasperPrint = JasperFillManager.fillReport(jasperReport, null, xmlDataSource);  
		JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
		
		
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(new MediaType("application", "pdf"));
		respHeaders.setContentDispositionFormData("attachment", "danfe.pdf");		
		
		
		File result = new File("src/test/resources/report/danfe.pdf");		
		InputStreamResource isr = new InputStreamResource(new FileInputStream(result));
		
		return new ResponseEntity<InputStreamResource>(isr, respHeaders, HttpStatus.OK);
	}
}
