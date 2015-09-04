package com.hadrion.nfe.port.adapters.xml;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Adicao;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ImportacaoItem;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.Intermediacao;
import com.hadrion.nfe.dominio.modelo.nf.item.importacao.ViaTransporte;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.util.DataUtil;

public class ImportacaoXmlTest extends AbstractXmlTest{
	
	private static final String XML = 
			"<DI>\r\n" + 
			"   <nDI>1</nDI>\r\n" + 
			"   <dDI>2015-09-01</dDI>\r\n" + 
			"   <xLocDesemb>SANTOS</xLocDesemb>\r\n" + 
			"   <UFDesemb>SP</UFDesemb>\r\n" + 
			"   <dDesemb>2015-08-31</dDesemb>\r\n" + 
			"   <tpViaTransp>MARITIMA</tpViaTransp>\r\n" + 
			"   <vAFRMM>1.23</vAFRMM>\r\n" + 
			"   <tpIntermedio>1</tpIntermedio>\r\n" + 
			"   <CNPJ>74230061000181</CNPJ>\r\n" + 
			"   <UFTerceiro>BA</UFTerceiro>\r\n" + 
			"	<cExportador>1a</cExportador>\r\n" + 
			"	<adi>\r\n" + 
			"       <nAdicao>321</nAdicao>\r\n" + 
			"       <nSeqAdicC>123</nSeqAdicC>\r\n" + 
			"   	<cFabricante>abc2030</cFabricante>\r\n" + 
			"   	<vDescDI>0.00</vDescDI>\r\n" + 
			"	</adi>\r\n" + 
			"</DI>\r\n";
	
	@Before
	public void setUp() {
		super.setUp();
		xstream.alias("DI", ImportacaoItem.class);
	}
	
	@Test
	public void serializar(){
		System.out.println(XML);
		System.out.println(toXML(importacaoItem()));
		assertXMLEquals(XML,toXML(importacaoItem()));
	}
	
	//@Test
	public void deserializar(){
		assertEquals(importacaoItem(),fromXML(XML));
	}
	
	private ImportacaoItem importacaoItem(){
		
		return new ImportacaoItem(1,DataUtil.data("01/09/2015"),"SANTOS",Uf.SP,DataUtil.data("31/08/2015"),ViaTransporte.MARITIMA, "1a",
				new Dinheiro(1.23),Intermediacao.CONTA_PROPRIA,new Cnpj(74230061000181L),Uf.BA,123,456,
				Arrays.asList(adicao()));
	}	
	private Adicao adicao(){
		return new Adicao(321,123,"abc2030",Dinheiro.ZERO);
	}
	
}
