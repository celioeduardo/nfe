package com.hadrion.nfe.port.adapters.portal.autorizacao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.ReciboLote;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;

public class RetornoAutorizacaoDeserializadorTest {
	public static final String XML_OK = 
			"<nfeAutorizacaoLoteResult xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeAutorizacao\">\r\n" + 
			"	<retEnviNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"3.10\">\r\n" + 
			"		<tpAmb>2</tpAmb>\r\n" + 
			"		<verAplic>13_2_35</verAplic>\r\n" + 
			"		<cStat>103</cStat>\r\n" + 
			"		<xMotivo>Lote recebido com sucesso</xMotivo>\r\n" + 
			"		<cUF>31</cUF>\r\n" + 
			"		<dhRecbto>2014-09-19T09:59:40-03:00</dhRecbto>\r\n" + 
			"		<infRec>\r\n" + 
			"			<nRec>310000032678611</nRec>\r\n" + 
			"			<tMed>1</tMed>\r\n" + 
			"		</infRec>\r\n" + 
			"	</retEnviNFe>\r\n" + 
			"</nfeAutorizacaoLoteResult>";
	
	public static final String XML_NOK = 
			"<nfeAutorizacaoLoteResult xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeAutorizacao\">\r\n" + 
			"	<retEnviNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"3.10\">\r\n" + 
			"		<tpAmb>2</tpAmb>\r\n" + 
			"		<verAplic>13_2_35</verAplic>\r\n" + 
			"		<cStat>999</cStat>\r\n" + 
			"		<xMotivo>Erro não catalogado</xMotivo>\r\n" + 
			"		<cUF>31</cUF>\r\n" + 
			"	</retEnviNFe>\r\n" + 
			"</nfeAutorizacaoLoteResult>";
	
	RetornoAutorizacaoDeserializador deserializador;
	
	@Before
	public void setUp(){
		 
	}
	
	@Test
	public void deserializarOk(){
		deserializador = new RetornoAutorizacaoDeserializador(XML_OK);
		assertEquals(new RetornoAutorizacao(
				new ReciboLote(new NumeroReciboLote("310000032678611"))),
				deserializador.deserializar());
	}
	
	@Test
	public void deserializarNok(){
		deserializador = new RetornoAutorizacaoDeserializador(XML_NOK);
		assertEquals(new RetornoAutorizacao(new Mensagem(999, "Erro não catalogado")),
				deserializador.deserializar());
	}
}
