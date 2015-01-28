package com.hadrion.nfe.port.adapters.portal.evento;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoEvento;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoLoteEvento;
import com.hadrion.util.DataUtil;

public class RetornoEventoDeserializadorTest {
	public static final String XML_EVENTO_REGISTRADO = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
			"<nfeRecepcaoEventoResult xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">"+
			"<retEnvEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">\n" + 
			"  <idLote>1</idLote>\n" + 
			"  <tpAmb>2</tpAmb>\n" + 
			"  <verAplic>13_2_55</verAplic>\n" + 
			"  <cOrgao>31</cOrgao>\n" + 
			"  <cStat>135</cStat>\n" + 
			"  <xMotivo>Evento registrado e vinculado a NF-e</xMotivo>\n" + 
			"  <retEvento versao=\"1.00\">\n" + 
			"    <infEvento>\n" + 
			"      <tpAmb>2</tpAmb>\n" + 
			"      <verAplic>13_2_55</verAplic>\n" + 
			"      <cOrgao>31</cOrgao>\n" + 
			"      <chNFe>31141286675642000106550020002048171000000014</chNFe>\n" + 
			"      <tpEvento>110111</tpEvento>\n" + 
			"      <xEvento>CANCELAMENTO</xEvento>\n" + 
			"      <nSeqEvento>1</nSeqEvento>\n" + 
			"      <nProt>131151658585018</nProt>"+
			"      <dhRegEvento>2015-01-27T17:47:36-02:00</dhRegEvento>\n" + 
			"    </infEvento>\n" + 
			"  </retEvento>\n" + 
			"</retEnvEvento></nfeRecepcaoEventoResult>";
	
	public static final String XML_EVENTO_PROCESSADO = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + 
			"<nfeRecepcaoEventoResult xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">"+
			"<retEnvEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">\n" + 
			"  <idLote>1</idLote>\n" + 
			"  <tpAmb>2</tpAmb>\n" + 
			"  <verAplic>13_2_55</verAplic>\n" + 
			"  <cOrgao>31</cOrgao>\n" + 
			"  <cStat>128</cStat>\n" + 
			"  <xMotivo>Lote de Evento Processado.</xMotivo>\n" + 
			"  <retEvento versao=\"1.00\">\n" + 
			"    <infEvento>\n" + 
			"      <tpAmb>2</tpAmb>\n" + 
			"      <verAplic>13_2_55</verAplic>\n" + 
			"      <cOrgao>31</cOrgao>\n" + 
			"      <cStat>220</cStat>\n" + 
			"      <xMotivo>Rejeicao: Prazo de Cancelamento Superior ao Previsto na Legislacao</xMotivo>\n" + 
			"      <chNFe>31141286675642000106550020002048171000000014</chNFe>\n" + 
			"      <tpEvento>110111</tpEvento>\n" + 
			"      <xEvento>CANCELAMENTO</xEvento>\n" + 
			"      <nSeqEvento>1</nSeqEvento>\n" + 
			"      <dhRegEvento>2015-01-27T17:47:36-02:00</dhRegEvento>\n" + 
			"    </infEvento>\n" + 
			"  </retEvento>\n" + 
			"</retEnvEvento></nfeRecepcaoEventoResult>";
	
	public static final String XML_EVENTO_REJEITADO = 
			"<nfeRecepcaoEventoResult xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/RecepcaoEvento\">\n" + 
			"  <retEnvEvento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">\n" + 
			"    <idLote />\n" + 
			"    <tpAmb>2</tpAmb>\n" + 
			"    <verAplic>SVC_2.1.1</verAplic>\n" + 
			"    <cOrgao>31</cOrgao>\n" + 
			"    <cStat>588</cStat>\n" + 
			"    <xMotivo>Rejeicao: Nao e permitida a presenca de caracteres de edicao no inicio/fim da mensagem ou entre as tags da mensagem</xMotivo>\n" + 
			"  </retEnvEvento>\n" + 
			"</nfeRecepcaoEventoResult>";
	
	RetornoLoteEventoDeserializador deserializador;
	
	@Test
	public void deserializarEventoHomologado(){
		deserializador = new RetornoLoteEventoDeserializador(XML_EVENTO_REGISTRADO);
		assertEquals(
			new RetornoLoteEvento(new Mensagem(135,"Evento registrado e vinculado a NF-e"), 
				new RetornoEvento(
						new ChaveAcesso("31141286675642000106550020002048171000000014"),
						110111,
						"CANCELAMENTO",
						null, 
						DataUtil.dataHora("27/01/2015 17:47:36", "GMT-02:00"),
						new NumeroProtocolo("131151658585018"))),
			deserializador.deserializar());
	}
	@Test
	public void deserializarOk(){
		deserializador = new RetornoLoteEventoDeserializador(XML_EVENTO_PROCESSADO);
		assertEquals(
				new RetornoLoteEvento(new Mensagem(128,"Lote de Evento Processado."), 
						new RetornoEvento(
								new ChaveAcesso("31141286675642000106550020002048171000000014"),
								110111,
								"CANCELAMENTO",
								new Mensagem(220,"Rejeicao: Prazo de Cancelamento Superior ao Previsto na Legislacao"), 
								DataUtil.dataHora("27/01/2015 17:47:36", "GMT-02:00"),
								null)),
								deserializador.deserializar());
	}
	
	@Test
	public void deserializarNok(){
		deserializador = new RetornoLoteEventoDeserializador(XML_EVENTO_REJEITADO);
		assertEquals(
				new RetornoLoteEvento(new Mensagem(588,"Rejeicao: Nao e permitida a presenca de caracteres de edicao no inicio/fim da mensagem ou entre as tags da mensagem")),
				deserializador.deserializar());
	}
}
