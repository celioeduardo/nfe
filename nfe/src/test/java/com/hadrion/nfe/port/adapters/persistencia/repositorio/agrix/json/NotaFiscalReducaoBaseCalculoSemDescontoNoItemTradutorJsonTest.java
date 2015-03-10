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

public class NotaFiscalReducaoBaseCalculoSemDescontoNoItemTradutorJsonTest {

	@Test
	public void traduzirNota() throws IOException{
		
		final File json =FileUtils.getFile("src","test", "resources", "json", "notaRbcSemDesc.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(
				FileUtils.readFileToString(json),
				Ambiente.HOMOLOGACAO);
		NotaFiscal nf = tradutor.converterNotaFiscal()[0];
		
		assertEquals("H-0CB660D3F9E57581E050007F01004EF7",nf.notaFiscalId().id());
		
		assertEquals(new Dinheiro(37.55),nf.total());
		assertEquals(new Dinheiro(39.69),nf.totalProdutos());
		
		assertEquals(new Dinheiro(27.79),nf.totalBaseCalculoIcms());
		assertEquals(new Dinheiro(5.01),nf.totalIcms());
		assertEquals(new Dinheiro(2.14),nf.totalDesconto());
		assertEquals(new Dinheiro(6.74),nf.totalValorAproximadoTributos());
		
		Item item = null;

		item = nf.item(0);
		assertEquals(new Quantidade(2.0),item.produto().quantidadeComercial());
		assertEquals(9.905,item.produto().valorUnitarioComercializacao(),0.0001);
		assertEquals(new Dinheiro(19.81),item.produto().totalBruto());
		assertEquals(new Dinheiro(13.87),item.imposto().icms().baseCalculo());
		assertEquals(new Dinheiro(2.50),item.imposto().icms().valor());
		assertEquals(new Aliquota(18),item.imposto().icms().aliquota());
		assertEquals(new Dinheiro(1.07),item.valorDesconto());	

	}
		
}
