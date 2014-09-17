package com.hadrion.nfe.port.adapters.portal.ws;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.port.adapters.portal.ws.Cabecalho;
import com.hadrion.nfe.port.adapters.xml.AbstractXmlTest;

public class CabecalhoTest extends AbstractXmlTest {

	@Test
	public void cabecalhoAutorizacao(){
		Cabecalho cabecalho = new Cabecalho(Uf.MG);
		assertXMLEquals(
			"<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeAutorizacao3\">\r\n" + 
			"	<cUF>31</cUF>\r\n" + 
			"	<versaoDados>3.10</versaoDados>\r\n" + 
			"</nfeCabecMsg>", cabecalho.autorizacao());
	}
	
	@Test
	public void cabecalhoRetornoAutorizacao(){
		Cabecalho cabecalho = new Cabecalho(Uf.MG);
		assertXMLEquals(
			"<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao3\">\r\n" + 
			"	<cUF>31</cUF>\r\n" + 
			"	<versaoDados>3.10</versaoDados>\r\n" + 
			"</nfeCabecMsg>",
			cabecalho.retornoAutorizacao());
	}
	
}
