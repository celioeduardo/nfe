package com.hadrion.util.report;

import static com.hadrion.util.xml.XmlUtil.xmlParaString;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRXmlUtils;

import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalFixture;

public class ReportTest {
	JasperReport jasperReport;
	JasperPrint jasperPrint;
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
    	
        jasperReport = JasperCompileManager.compileReport("src/test/resources/report/test.jrxml");

		Vector<NotaFiscal> notas = new Vector<NotaFiscal>();
		notas.add(nf());
        
        jasperPrint = JasperFillManager.fillReport(jasperReport, null, new NotaFiscalJRDataSource(notas));
         
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/test.pdf");
        
    }   
    @Ignore @Test
    public void novoParametros() throws JRException{
    	
    	jasperReport = JasperCompileManager.compileReport("src/test/resources/report/danfe.jrxml");
    	
    	Map<String,Object> parameters= new HashMap<String, Object>();
    	parameters.put("NaturezaOperacao", nf().naturezaOperacao());
    	
    	jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
    	
    	JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
    	
    }   
    @Test
    public void novoXml() throws JRException{
    	
    	Document xml = JRXmlUtils.parse(JRLoader.getLocationInputStream("src/test/resources/report/nfe.xml"));
    	
        JRXmlDataSource xmlDataSource = new JRXmlDataSource(xml,"/nfeProc/NFe/infNFe/det");
        
        jasperReport = JasperCompileManager.compileReport("src/test/resources/report/danfe.jrxml");
        jasperPrint = JasperFillManager.fillReport(jasperReport, null, xmlDataSource);  
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
   	
    }
    @Ignore @Test
    public void novoXmlDataSource() throws JRException{
    	
        File xmlFile = new File("src/test/resources/report/nfe.xml");  
        JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlFile,"/nfeProc/NFe/infNFe/det");
        
        jasperReport = JasperCompileManager.compileReport("src/test/resources/report/danfe.jrxml");
        jasperPrint = JasperFillManager.fillReport(jasperReport, null, xmlDataSource);  
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
    }
    @Ignore @Test
    public void novoXmlDataSourceInputStream() throws JRException{
    	
    	Map<String,Object> parameters = new HashMap<String,Object>();
    	//parameters.put("Trans_PesoB", "0");
    	
    	File xmlFile = new File("src/test/resources/report/nfe.xml");  
    	JRXmlDataSource xmlDataSource = new JRXmlDataSource(xmlFile,"/nfeProc/NFe/infNFe/det");
    	
    	jasperReport = JasperCompileManager.compileReport("src/test/resources/report/danfe.jrxml");
    	jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, xmlDataSource);  
    	JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/danfe.pdf");
    	//JasperViewer.viewReport(jasperPrint, true); 
    }
    private NotaFiscal nf(){
		return NotaFiscalFixture.nf();
	}
    
    
}

