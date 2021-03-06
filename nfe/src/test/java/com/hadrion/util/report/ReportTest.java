package com.hadrion.util.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRXmlUtils;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;

import com.hadrion.nfe.dominio.config.Application;
import com.hadrion.nfe.dominio.modelo.empresa.LogoFixture;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalSerializador;
import com.hadrion.util.DataUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class ReportTest {
	@Autowired
	private NotaFiscalRepositorio repositorio;
	private JasperReport jasperReport;
	private JasperPrint jasperPrint;
	class NotaFiscalJRDataSource implements JRDataSource {

		private Iterator<NotaFiscal> iterator;
		private NotaFiscal cursor;
		
		public NotaFiscalJRDataSource(Vector<NotaFiscal> notas){
			super();
			iterator = notas.iterator();
		}
		
		@Override
		public boolean next() throws JRException {
			boolean retorno = iterator.hasNext();
			if (retorno)
				cursor=iterator.next();
			return retorno;
		}

		@Override
		public Object getFieldValue(JRField campo) throws JRException {
			
			NotaFiscal nf = cursor;
			
			if (campo.getName().equals("NotaFiscalId"))
				return nf.notaFiscalId().id();
			if (campo.getName().equals("NaturezaOperacao"))
				return nf.naturezaOperacao();
			
			return null;
		}
		
	}
	
    @Ignore @Test
	public void novoDataSource() throws JRException{
    	
        jasperReport = JasperCompileManager.compileReport(
        		getClass().getClassLoader().getResourceAsStream("report/test.jrxml"));

		Vector<NotaFiscal> notas = new Vector<NotaFiscal>();
		notas.add(nf());
        
        jasperPrint = JasperFillManager.fillReport(jasperReport, null, new NotaFiscalJRDataSource(notas));
         
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/test.pdf");
        
    }   
    @Test @Ignore
    public void novoParametros() throws JRException{
    	
    	jasperReport = JasperCompileManager.compileReport(
    			getClass().getClassLoader().getResourceAsStream("report/danfe.jrxml"));
    	
    	Map<String,Object> parameters= new HashMap<String, Object>();
    	parameters.put("NaturezaOperacao", nf().naturezaOperacao());
    	parameters.put("Logo", LogoFixture.logoFile());
    	
    	jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
    	
    	JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
    	
    }   
    
    @Test @Ignore
    public void novoXml() throws JRException, FileNotFoundException{
    	
    	Document xml = JRXmlUtils.parse(
    			getClass().getClassLoader().getResourceAsStream("report/nfe.xml"));
    	
        JRXmlDataSource xmlDataSource = new JRXmlDataSource(xml,"/nfeProc/NFe/infNFe/det");
        
    	Map<String,Object> parameters= new HashMap<String, Object>();
    	//parameters.put("NaturezaOperacao", nf().naturezaOperacao());
    	//parameters.put("Logo", LogoFixture.logoInputStream());
    	for (int i = 0; i < 90; i++) {
			parameters.put("FAT_NUMERO"+i,i+"A");        
			parameters.put("FAT_VENCIMENTO"+i, DataUtil.agora());        
			parameters.put("FAT_VALOR"+i, 50.23*i);        
			
		}
        
        //jasperReport = JasperCompileManager.compileReport(getClass().getClassLoader().getResourceAsStream("report/danfe.jrxml"));
        
    	InputStream reportStream = getClass().getClassLoader().getResourceAsStream("report/danfe.jasper");
    	jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
    	jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, xmlDataSource);  
    	JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
		
        
        
    }
    
    @Test @Ignore
    public void novoXmlDataSource() throws JRException{
    	
        JRXmlDataSource xmlDataSource = new JRXmlDataSource(
        		getClass().getClassLoader().getResourceAsStream("report/nfe.xml"),
        		"/nfeProc/NFe/infNFe/det");
        
        jasperReport = JasperCompileManager.compileReport(
        		getClass().getClassLoader().getResourceAsStream("report/danfe.jrxml"));
        jasperPrint = JasperFillManager.fillReport(jasperReport, null, xmlDataSource);  
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
    }
    @Test @Ignore
    public void novoXmlDataSourceFromFile() throws JRException, IOException{
    	NotaFiscalSerializador serializador = new NotaFiscalSerializador();
    	InputStream xmlFile = IOUtils.toInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n" + 
    			"<nfeProc>\r\n" +
    			serializador.serializar(repositorio.notaFiscalPeloId(new NotaFiscalId("03F79B1D8D397592E050007F01005CC8"))) +
    			"</nfeProc>", "UTF-8");
    	
    	JRXmlDataSource xmlDataSource = new JRXmlDataSource( xmlFile,"/nfeProc/NFe/infNFe/det");    	
    	jasperReport = JasperCompileManager.compileReport(
    			getClass().getClassLoader().getResourceAsStream("report/danfe.jrxml"));
    	jasperPrint = JasperFillManager.fillReport(jasperReport, null, xmlDataSource);  
    	JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
    }
    @Ignore @Test
    public void novoXmlDataSourceInputStream() throws JRException{
    	
    	Map<String,Object> parameters = new HashMap<String,Object>();
    	//parameters.put("Trans_PesoB", "0");
    	
    	File xmlFile = new File("src/test/resources/report/nfe.xml");  
    	JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlFile,"/nfeProc/NFe/infNFe/det");
    	
    	jasperReport = JasperCompileManager.compileReport(
    			getClass().getClassLoader().getResourceAsStream("report/danfe.jrxml"));
    	jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, xmlDataSource);  
    	JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
    	//JasperViewer.viewReport(jasperPrint, true); 
    }
    private NotaFiscal nf(){
		return NotaFiscalFixture.nfEmProducao();
	}
    
    @Test @Ignore
    public void cceXmlDataSource() throws JRException, URISyntaxException, IOException{
    	
        JRXmlDataSource xmlDataSource = new JRXmlDataSource(
        		getClass().getClassLoader().getResourceAsStream("report/cce.xml"),"/");

        jasperReport = JasperCompileManager.compileReport(
        		getClass().getClassLoader().getResourceAsStream("report/cce.jrxml"));
        jasperPrint = JasperFillManager.fillReport(jasperReport, null, xmlDataSource);  
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/cce.pdf");
    }
    
}

