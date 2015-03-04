package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.NotaFiscalTradutorJson;
import com.hadrion.nfe.tipos.Aliquota;
import com.hadrion.nfe.tipos.Dinheiro;
import com.hadrion.nfe.tipos.Quantidade;

public class NotaFiscalReducaoBaseCalculoTradutorJsonTest {

	@Test
	public void traduzirNota() throws IOException{
		
		final File json =FileUtils.getFile("src","test", "resources", "json", "notaRbc.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(
				FileUtils.readFileToString(json),
				Ambiente.HOMOLOGACAO);
		NotaFiscal nf = tradutor.converterNotaFiscal()[0];
		
		assertEquals("H-104CFC948C0EDAE9E050A8C03B0018C3",nf.notaFiscalId().id());
		
		assertEquals(new Dinheiro(1791.48),nf.totalProdutos());
		assertEquals(new Dinheiro(1646.57),nf.total());
		
		assertEquals(new Dinheiro(1218.38),nf.totalBaseCalculoIcms());
		assertEquals(new Dinheiro(219.31),nf.totalIcms());
		assertEquals(new Dinheiro(144.91),nf.totalDesconto());
		
		Item item = null;
		
		item = nf.item(2);
		assertEquals(new Quantidade(2.0),item.produto().quantidadeComercial());
		assertEquals(229.93,item.produto().valorUnitarioComercializacao(),0.0001);
		assertEquals(new Dinheiro(459.86),item.produto().totalBruto());
		assertEquals(new Dinheiro(312.75),item.imposto().icms().baseCalculo());
		assertEquals(new Dinheiro(56.3),item.imposto().icms().valor());
		assertEquals(new Aliquota(18),item.imposto().icms().aliquota());
		
		item = nf.item(0);
		assertEquals(new Quantidade(62.0),item.produto().quantidadeComercial());
		assertEquals(10.4473,item.produto().valorUnitarioComercializacao(),0.0001);
		assertEquals(new Dinheiro(647.73),item.produto().totalBruto());
		assertEquals(new Dinheiro(440.52),item.imposto().icms().baseCalculo());
		assertEquals(new Dinheiro(79.29),item.imposto().icms().valor());
		assertEquals(new Aliquota(18),item.imposto().icms().aliquota());
		
		item = nf.item(1);
		assertEquals(new Quantidade(5.0),item.produto().quantidadeComercial());
		assertEquals(51.344,item.produto().valorUnitarioComercializacao(),0.0001);
		assertEquals(new Dinheiro(256.72),item.produto().totalBruto());
		assertEquals(new Dinheiro(174.59),item.imposto().icms().baseCalculo());
		assertEquals(new Dinheiro(31.43),item.imposto().icms().valor());
		assertEquals(new Aliquota(18),item.imposto().icms().aliquota());
		
		item = nf.item(3);
		assertEquals(new Quantidade(12.0),item.produto().quantidadeComercial());
		assertEquals(35.5975,item.produto().valorUnitarioComercializacao(),0.0001);
		assertEquals(new Dinheiro(427.17),item.produto().totalBruto());
		assertEquals(new Dinheiro(290.52),item.imposto().icms().baseCalculo());
		assertEquals(new Dinheiro(52.29),item.imposto().icms().valor());
		assertEquals(new Aliquota(18),item.imposto().icms().aliquota());
	}
		
}
