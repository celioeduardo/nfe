package com.hadrion.util.report;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.junit.Test;

public class ReportTest {
	JasperReport jasperReport;
	JasperPrint jasperPrint;
    @Test
	public void novo() throws JRException{
    	
        jasperReport = JasperCompileManager.compileReport("src/test/resources/report/test.jrxml");

        Map <String, Object> parameters = new HashMap<String, Object>();
        parameters.put("Chave", null);
        
        //jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanArrayDataSource(new NotaFiscal[]{nf()}));
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
         
        JasperExportManager.exportReportToPdfFile(jasperPrint, "src/test/resources/report/test.pdf");
        
    }   
//	private NotaFiscal nf(){
//		return NotaFiscalFixture.nf();
//	}
    
}

