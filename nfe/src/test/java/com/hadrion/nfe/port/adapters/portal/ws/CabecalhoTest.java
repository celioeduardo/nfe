package com.hadrion.nfe.port.adapters.portal.ws;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.port.adapters.portal.ws.Cabecalho;
import com.hadrion.nfe.port.adapters.xml.AbstractXmlTest;

public class CabecalhoTest extends AbstractXmlTest {

	@Test
	public void cabecalhoAutorizacaoMg(){
		Cabecalho cabecalho = new Cabecalho(Uf.MG);
		assertXMLEquals(
			"<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeAutorizacao\">\r\n" + 
			"	<cUF>31</cUF>\r\n" + 
			"	<versaoDados>3.10</versaoDados>\r\n" + 
			"</nfeCabecMsg>", cabecalho.autorizacao());
	}
	
	@Test
	public void cabecalhoRetornoAutorizacaoMg(){
		Cabecalho cabecalho = new Cabecalho(Uf.MG);
		assertXMLEquals(
			"<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao\">\r\n" + 
			"	<cUF>31</cUF>\r\n" + 
			"	<versaoDados>3.10</versaoDados>\r\n" + 
			"</nfeCabecMsg>",
			cabecalho.retornoAutorizacao());
	}
	@Test
	public void cabecalhoAutorizacaoSp(){
		Cabecalho cabecalho = new Cabecalho(Uf.SP);
		assertXMLEquals(
			"<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeAutorizacao\">\r\n" + 
			"	<cUF>35</cUF>\r\n" + 
			"	<versaoDados>3.10</versaoDados>\r\n" + 
			"</nfeCabecMsg>", cabecalho.autorizacao());
	}
	
	@Test
	public void cabecalhoRetornoAutorizacaoSp(){
		Cabecalho cabecalho = new Cabecalho(Uf.SP);
		assertXMLEquals(
			"<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao\">\r\n" + 
			"	<cUF>35</cUF>\r\n" + 
			"	<versaoDados>3.10</versaoDados>\r\n" + 
			"</nfeCabecMsg>",
			cabecalho.retornoAutorizacao());
	}	
	@Test
	public void cabecalhoAutorizacaoBa(){
		Cabecalho cabecalho = new Cabecalho(Uf.BA);
		assertXMLEquals(
				"<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeAutorizacao\">\r\n" + 
						"	<cUF>29</cUF>\r\n" + 
						"	<versaoDados>3.10</versaoDados>\r\n" + 
						"</nfeCabecMsg>", cabecalho.autorizacao());
	}
	
	@Test
	public void cabecalhoRetornoAutorizacaoBa(){
		Cabecalho cabecalho = new Cabecalho(Uf.BA);
		assertXMLEquals(
				"<nfeCabecMsg xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao\">\r\n" + 
						"	<cUF>29</cUF>\r\n" + 
						"	<versaoDados>3.10</versaoDados>\r\n" + 
						"</nfeCabecMsg>",
						cabecalho.retornoAutorizacao());
	}	
}
