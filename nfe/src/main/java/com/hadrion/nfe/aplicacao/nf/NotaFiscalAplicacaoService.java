package com.hadrion.nfe.aplicacao.nf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.lote.EnviarLoteService;
import com.hadrion.nfe.dominio.modelo.lote.GeracaoLoteService;
import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.LoteRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.DescritorNotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalSerializador;

@Service
@Transactional
public class NotaFiscalAplicacaoService {
	@Autowired
	private GeracaoLoteService geracaoLoteService;
	
	@Autowired
	private EnviarLoteService enviarLoteService;
	
	@Autowired
	private LoteRepositorio loteRepositorio;
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	public List<NotaFiscalData> notasFicaisPendentesAutorizacaoResumo(
			Ambiente ambiente,
			Double empresa,Double filial,
			Date inicio,Date fim,String usuario,String notaFiscalId){
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		NotaFiscalId notaFiscalIdFiltro = null;
		
		if (notaFiscalId!=null)
			notaFiscalIdFiltro=new NotaFiscalId(notaFiscalId);
		
		for (DescritorNotaFiscal nf : notaFiscalRepositorio.notasPendentesAutorizacaoResumo(
				ambiente,
				empresa,filial,inicio,fim,usuario,notaFiscalIdFiltro)) {
			result.add(new NotaFiscalData(nf.notaFiscalId().id(),
					nf.numero(),
					String.valueOf(nf.serie().numero()),
					nf.emissao(),
					nf.valor().valor(),
					nf.publicoTipo(),
					nf.publicoCodigo(),
					nf.publicoNome(),
					nf.tipo(),
					nf.mensagem() != null ? new Long(nf.mensagem().codigo()) : null,
					nf.mensagem() != null ? nf.mensagem().descricao() : null));
		}
		
		return result;
	}
	public List<NotaFiscalData> notasFicaisPendentesAutorizacao(Ambiente ambiente){
		List<NotaFiscalData> result = new ArrayList<NotaFiscalData>();
		
		for (NotaFiscal nf : notaFiscalRepositorio.notasPendentesAutorizacao(null,ambiente)) {
			result.add(construir(nf));
		}
		
		return result;
	}
	
	private NotaFiscalData construir(NotaFiscal nf){
		return new NotaFiscalData(nf.notaFiscalId().id(),
				nf.numero(),
				String.valueOf(nf.serie().numero()),
				nf.emissao(),
				nf.total().valor(),
				nf.destinatario().razaoSocial(),
				nf.tipoOperacao().toString(),
				nf.mensagem() != null ? new Long(nf.mensagem().codigo()) : null,
				nf.mensagem() != null ? nf.mensagem().descricao() : null);
	}
	
	public ResponseEntity<InputStreamResource> obterDanfe(String notaFiscalId) throws IOException,JRException{
		
		JasperReport jasperReport;
		JasperPrint jasperPrint;

		NotaFiscalSerializador serializador = new NotaFiscalSerializador();
		InputStream xmlFile = IOUtils.toInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
    			"<nfeProc>\r\n" +
				serializador.serializar(notaFiscalRepositorio.notaFiscalPeloId(new NotaFiscalId(notaFiscalId))) +
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
	
	public String enviarNotas(EnviarNotasComando comando){
		Ambiente ambiente = Ambiente.valueOf(comando.getAmbiente());
		
		Lote lote = null;
		
		if (ambiente == Ambiente.PRODUCAO)
			lote =  geracaoLoteService.gerarLoteEmProducao(notas(comando.getIds(),ambiente));
		else
			lote =  geracaoLoteService.gerarLoteEmHomologacao(notas(comando.getIds(),ambiente));
		
		loteRepositorio.salvar(lote);
		
		enviarLoteService.enviar(lote);
		
		loteRepositorio.salvar(lote);
		
		return String.valueOf(lote.loteId());
		
	}
	
	public void definirNotaComoRejeitada(DefinirNotaComoRejeitadaComando comando) {
		NotaFiscal nota = nota(comando.getNotaFiscalId());
		if (nota != null)
			nota.rejeitada(new Mensagem(comando.getMsgCodigo(), comando.getMsgDescricao()));
		notaFiscalRepositorio.salvar(nota);
	}
	
	private NotaFiscal nota(String notaFiscalId){
		return notaFiscalRepositorio.notaFiscalPeloId(new NotaFiscalId(notaFiscalId));
	}
	
	private Set<NotaFiscal> notas(List<String> ids, Ambiente ambiente){
		List<NotaFiscalId> listaId = new ArrayList<NotaFiscalId>();
		
		for (String notaFiscalId : ids) 
			listaId.add(new NotaFiscalId(notaFiscalId));
		
		return new HashSet<NotaFiscal>(notaFiscalRepositorio.notasPendentesAutorizacao(listaId,ambiente));
	}
	
}
