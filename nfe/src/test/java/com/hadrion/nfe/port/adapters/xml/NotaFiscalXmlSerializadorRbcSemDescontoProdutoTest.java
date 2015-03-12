package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.xml.xpath.Jaxp13XPathTemplate;
import org.springframework.xml.xpath.XPathOperations;
import org.w3c.dom.Document;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.NotaFiscalTradutorJson;
import com.hadrion.nfe.port.adapters.xml.nf.NotaFiscalSerializador;
import com.hadrion.util.xml.XmlUtil;

public class NotaFiscalXmlSerializadorRbcSemDescontoProdutoTest extends AbstractXmlTest{
	
	private NotaFiscal nf;
	
	XPathFactory xPathfactory = XPathFactory.newInstance();
	
	XPathOperations template = new Jaxp13XPathTemplate();
	
	@Before
	public void setUp() {
		super.setUp();
		
		HashMap<String, String> nameSpaces = new HashMap<String, String>();
		nameSpaces.put("", "http://www.portalfiscal.inf.br/nfe");
		((Jaxp13XPathTemplate) template).setNamespaces(nameSpaces);
	}
	
	@Test
	public void serializar() throws NoSuchAlgorithmException, IOException{
		final File json =FileUtils.getFile("src","test", "resources", "json", "notaRbcSemDesc.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(
				FileUtils.readFileToString(json),
				Ambiente.HOMOLOGACAO);
		
		nf = tradutor.converterNotaFiscal()[0];
		
		NotaFiscalSerializador serializador = 
				new NotaFiscalSerializador();
		
		Document xml = XmlUtil.parseXml(serializador.serializar(nf));
		
		Source source = new DOMSource(xml);
		
		assertEquals("31",template.evaluateAsString("/:NFe/:infNFe/:ide/:cUF", source));
		assertEquals("6002",template.evaluateAsString("/:NFe/:infNFe/:det[@nItem='1']/:prod/:cProd", source));
		assertEquals("1.07",template.evaluateAsString("/:NFe/:infNFe/:det[@nItem='1']/:prod/:vDesc", source));
		
		assertEquals("3887",template.evaluateAsString("/:NFe/:infNFe/:det[@nItem='2']/:prod/:cProd", source));
		assertEquals("1.07",template.evaluateAsString("/:NFe/:infNFe/:det[@nItem='2']/:prod/:vDesc", source));
		
		assertEquals("2.14",template.evaluateAsString("/:NFe/:infNFe/:total/:ICMSTot/:vDesc", source));
		
		assertEquals("39.69",template.evaluateAsString("/:NFe/:infNFe/:total/:ICMSTot/:vProd", source));
		assertEquals("37.55",template.evaluateAsString("/:NFe/:infNFe/:total/:ICMSTot/:vNF", source));
		
	}
	
}
