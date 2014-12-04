package com.hadrion.nfe.port.adapters.portal.autorizacao;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;
import com.hadrion.nfe.dominio.modelo.nf.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.ReciboLote;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.ProtocoloNotaProcessada;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.consulta.RetornoConsultaProcessamentoLote;
import com.hadrion.util.DataUtil;

public class RetornoConsultaProcessamentoLoteDeserializadorTest {
	public static final String XML_OK = 
			"<nfeRetAutorizacaoLoteResult xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NfeRetAutorizacao\">\n" + 
			"  <retConsReciNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"3.10\">\n" + 
			"    <tpAmb>2</tpAmb>\n" + 
			"    <verAplic>13_2_52</verAplic>\n" + 
			"    <nRec>310000035995659</nRec>\n" + 
			"    <cStat>104</cStat>\n" + 
			"    <xMotivo>Lote processado</xMotivo>\n" + 
			"    <cUF>31</cUF>\n" + 
			"    <dhRecbto>2014-12-04T17:43:57-02:00</dhRecbto>\n" + 
			"    <protNFe versao=\"3.10\">\n" + 
			"      <infProt>\n" + 
			"        <tpAmb>2</tpAmb>\n" + 
			"        <verAplic>13_2_51</verAplic>\n" + 
			"        <chNFe>31131016832651000420550010000199361002699180</chNFe>\n" + 
			"        <dhRecbto>2014-12-04T17:27:22-02:00</dhRecbto>\n" + 
			"        <digVal>M8De8U+VYtAbxqIATq06Ao9OEBs=</digVal>\n" + 
			"        <cStat>297</cStat>\n" + 
			"        <xMotivo>Rejeicao: Assinatura difere do calculado</xMotivo>\n" + 
			"      </infProt>\n" + 
			"    </protNFe>\n" + 
			"  </retConsReciNFe>\n" + 
			"</nfeRetAutorizacaoLoteResult>";
	
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
	
	RetornoConsultaProcessamentoLoteDeserializador deserializador;
	
	@Before
	public void setUp(){
		 
	}
	
	@Test
	public void deserializarOk(){
		deserializador = new RetornoConsultaProcessamentoLoteDeserializador(XML_OK);
		
		assertEquals(new RetornoConsultaProcessamentoLote(
				new Mensagem(104, "Lote Processado"),
				null,
				Arrays.asList(
					new ProtocoloNotaProcessada(
						DataUtil.dataHora("04/12/2014 17:27:22", "GMT-02:00"), 
						null, 
						new Mensagem(297, "Rejeicao: assinatura difere do calculado"),
						new ChaveAcesso("31131016832651000420550010000199361002699180")))),
			deserializador.deserializar());
	}
	
//	@Test
//	public void deserializarNok(){
//		deserializador = new RetornoAutorizacaoDeserializador(XML_NOK);
//		assertEquals(new RetornoAutorizacao(new Mensagem(999, "Erro não catalogado")),
//				deserializador.deserializar());
//	}
}
