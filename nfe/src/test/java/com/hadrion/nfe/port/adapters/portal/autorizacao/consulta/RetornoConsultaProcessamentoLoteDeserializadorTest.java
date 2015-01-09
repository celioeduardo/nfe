package com.hadrion.nfe.port.adapters.portal.autorizacao.consulta;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
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
			"    <protNFe versao=\"3.10\">\n" + 
			"      <infProt>\n" + 
			"        <tpAmb>2</tpAmb>\n" + 
			"        <verAplic>13_2_51</verAplic>\n" + 
			"        <chNFe>31131016832651000420550010000011111002699187</chNFe>\n" + 
			"        <dhRecbto>2014-12-04T17:27:22-02:00</dhRecbto>\n" + 
			"        <nProt>135140720735769</nProt>\n" + 
			"        <digVal>M8De8U+VYtAbxqIATq06Ao9OEBs=</digVal>\n" + 
			"        <cStat>100</cStat>\n" + 
			"        <xMotivo>Autorizado o uso da NF-e</xMotivo>\n" + 
			"      </infProt>\n" + 
			"    </protNFe>\n" + 
			"  </retConsReciNFe>\n" + 
			"</nfeRetAutorizacaoLoteResult>";
	
	RetornoConsultaProcessamentoLoteDeserializador deserializador;
	
	@Before
	public void setUp(){}
	
	@Test
	public void deserializarOk(){
		deserializador = new RetornoConsultaProcessamentoLoteDeserializador(XML_OK);
		
		assertEquals(new RetornoConsultaProcessamentoLote(
				Ambiente.HOMOLOGACAO,
				new Mensagem(104, "Lote processado"),
				null,
				Arrays.asList(
					new ProtocoloNotaProcessada(
						DataUtil.dataHora("04/12/2014 17:27:22", "GMT-02:00"), 
						null, 
						new Mensagem(297, "Rejeicao: Assinatura difere do calculado"),
						new ChaveAcesso("31131016832651000420550010000199361002699180"),
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?><protNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"3.10\">\n" + 
						"      <infProt>\n" + 
						"        <tpAmb>2</tpAmb>\n" + 
						"        <verAplic>13_2_51</verAplic>\n" + 
						"        <chNFe>31131016832651000420550010000199361002699180</chNFe>\n" + 
						"        <dhRecbto>2014-12-04T17:27:22-02:00</dhRecbto>\n" + 
						"        <digVal>M8De8U+VYtAbxqIATq06Ao9OEBs=</digVal>\n" + 
						"        <cStat>297</cStat>\n" + 
						"        <xMotivo>Rejeicao: Assinatura difere do calculado</xMotivo>\n" + 
						"      </infProt>\n" + 
						"    </protNFe>"),
					new ProtocoloNotaProcessada(
						DataUtil.dataHora("04/12/2014 17:27:22", "GMT-02:00"), 
						new NumeroProtocolo("135140720735769"), 
						new Mensagem(100, "Autorizado o uso da NF-e"),
						new ChaveAcesso("31131016832651000420550010000011111002699187"),
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?><protNFe xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"3.10\">\n" + 
						"      <infProt>\n" + 
						"        <tpAmb>2</tpAmb>\n" + 
						"        <verAplic>13_2_51</verAplic>\n" + 
						"        <chNFe>31131016832651000420550010000011111002699187</chNFe>\n" + 
						"        <dhRecbto>2014-12-04T17:27:22-02:00</dhRecbto>\n" + 
						"        <nProt>135140720735769</nProt>\n" + 
						"        <digVal>M8De8U+VYtAbxqIATq06Ao9OEBs=</digVal>\n" + 
						"        <cStat>100</cStat>\n" + 
						"        <xMotivo>Autorizado o uso da NF-e</xMotivo>\n" + 
						"      </infProt>\n" + 
						"    </protNFe>")
				)),
			deserializador.deserializar());
	}
	
}
