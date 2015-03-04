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

public class NotaFiscalReducaoBaseCalculo2TradutorJsonTest {

	@Test
	public void traduzirNota() throws IOException{
		
		final File json =FileUtils.getFile("src","test", "resources", "json", "notaRbc2.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(
				FileUtils.readFileToString(json),
				Ambiente.HOMOLOGACAO);
		NotaFiscal nf = tradutor.converterNotaFiscal()[0];
		
		assertEquals("H-1063A2C92E4D5AFBE050A8C03B004DB0",nf.notaFiscalId().id());
		
		assertEquals(new Dinheiro(507.4),nf.totalProdutos());
		assertEquals(new Dinheiro(480.00),nf.total());
		
		assertEquals(new Dinheiro(355.18),nf.totalBaseCalculoIcms());
		assertEquals(new Dinheiro(63.93),nf.totalIcms());
		assertEquals(new Dinheiro(27.4),nf.totalDesconto());
		assertEquals(new Dinheiro(63.93),nf.totalValorAproximadoTributos());
		
		Item item = null;

		item = nf.item(0);
		assertEquals(new Quantidade(8.0),item.produto().quantidadeComercial());
		assertEquals(63.425,item.produto().valorUnitarioComercializacao(),0.0001);
		assertEquals(new Dinheiro(507.4),item.produto().totalBruto());
		assertEquals(new Dinheiro(355.18),item.imposto().icms().baseCalculo());
		assertEquals(new Dinheiro(63.93),item.imposto().icms().valor());
		assertEquals(new Aliquota(18),item.imposto().icms().aliquota());

	}
		
}
