package com.hadrion.nfe.port.adapters.portal.evento;

import java.io.IOException;

import org.custommonkey.xmlunit.XMLAssert;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.NumeroProtocolo;
import com.hadrion.nfe.port.adapters.xml.XStreamFabrica;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.util.DataUtil;
import com.thoughtworks.xstream.XStream;

public class EventoConverterTest {

	private static final String XML_CANCELAMENTO = 
			"<evento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">\n" + 
			"  <infEvento Id=\"ID1101113115018667564200010655002000263878100299390601\">\n" + 
			"    <cOrgao>31</cOrgao>\n" + 
			"    <tpAmb>1</tpAmb>\n" + 
			"    <CNPJ>86675642000106</CNPJ>\n" + 
			"    <chNFe>31150186675642000106550020002638781002993906</chNFe>\n" + 
			"    <dhEvento>2015-01-27T09:04:22-02:00</dhEvento>\n" + 
			"    <tpEvento>110111</tpEvento>\n" + 
			"    <nSeqEvento>1</nSeqEvento>\n" + 
			"    <verEvento>1.00</verEvento>\n" + 
			"    <detEvento versao=\"1.00\">\n" + 
			"      <descEvento>Cancelamento</descEvento>\n" + 
			"      <nProt>131151658576199</nProt>\n" + 
			"      <xJust>EMITIDA INDEVIDAMENTE.................</xJust>\n" + 
			"    </detEvento>\n" + 
			"  </infEvento>\n" + 
			"</evento>";
	
	private static final String XML_CCE = 
			"<evento xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.00\">\n" + 
			"  <infEvento Id=\"ID1101103115018667564200010655002000263878100299390601\">\n" + 
			"    <cOrgao>31</cOrgao>\n" + 
			"    <tpAmb>1</tpAmb>\n" + 
			"    <CNPJ>86675642000106</CNPJ>\n" + 
			"    <chNFe>31150186675642000106550020002638781002993906</chNFe>\n" + 
			"    <dhEvento>2015-01-27T09:04:22-02:00</dhEvento>\n" + 
			"    <tpEvento>110110</tpEvento>\n" + 
			"    <nSeqEvento>1</nSeqEvento>\n" + 
			"    <verEvento>1.00</verEvento>\n" + 
			"    <detEvento versao=\"1.00\">\n" + 
			"      <descEvento>Carta de Correcao</descEvento>\n" + 
			"      <xCorrecao>Teste de Carta de Correção</xCorrecao>\n" + 
			"      <xCondUso>A Carta de Correção é disciplinada pelo § 1º-A do art. 7º do Convênio S/N, de 15 de dezembro de 1970 e pode ser utilizada para regularização de erro ocorrido na emissão de documento fiscal, desde que o erro não esteja relacionado com: I - as variáveis que determinam o valor do imposto tais como: base de cálculo, alíquota, diferença de preço, quantidade, valor da operação ou da prestação; II - a correção de dados cadastrais que implique mudança do remetente ou do destinatário; III - a data de emissão ou de saída.</xCondUso>\n" + 
			"    </detEvento>\n" + 
			"  </infEvento>\n" + 
			"</evento>";
	
	@Test
	public void eventoCancelamento() throws SAXException, IOException{
		
		Evento evento = new EventoCancelamento(
				Uf.MG,
				Ambiente.PRODUCAO,
				new Cnpj(86675642000106L),
				new ChaveAcesso("31150186675642000106550020002638781002993906"),
				DataUtil.dataHora("27/01/2015 09:04:22", "GMT-02:00"), 
				1, //Sequencia do evento
				new NumeroProtocolo("131151658576199"),
				"EMITIDA INDEVIDAMENTE.................");
		
		XStream xstream = XStreamFabrica.criar();
		xstream.registerConverter(new EventoCancelamentoConverter());
		xstream.alias("evento", EventoCancelamento.class);
		
		XMLAssert.assertXMLEqual(XML_CANCELAMENTO,xstream.toXML(evento));
		
	}
	@Test
	public void eventoCartaCorrecao() throws SAXException, IOException{
		
		Evento evento = new EventoCartaCorrecao(
				Uf.MG,
				Ambiente.PRODUCAO,
				new Cnpj(86675642000106L),
				new ChaveAcesso("31150186675642000106550020002638781002993906"),
				DataUtil.dataHora("27/01/2015 09:04:22", "GMT-02:00"), 
				1, //Sequencia do evento
				"Teste de Carta de Correção");
		
		XStream xstream = XStreamFabrica.criar();
		xstream.registerConverter(new EventoCartaCorrecaoConverter());
		xstream.alias("evento", EventoCartaCorrecao.class);
		
		XMLAssert.assertXMLEqual(XML_CCE,xstream.toXML(evento));
		
	}
	
}
