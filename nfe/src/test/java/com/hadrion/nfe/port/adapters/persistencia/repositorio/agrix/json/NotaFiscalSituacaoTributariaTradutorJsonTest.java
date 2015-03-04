package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.port.adapters.agrix.repositorio.json.NotaFiscalTradutorJson;
import com.hadrion.nfe.tipos.Dinheiro;

public class NotaFiscalSituacaoTributariaTradutorJsonTest {

	@Test
	public void traduzirNota() throws IOException{
		
		final File json =FileUtils.getFile("src","test", "resources", "json", "notaSt.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(
				FileUtils.readFileToString(json),
				Ambiente.HOMOLOGACAO);
		NotaFiscal nf = tradutor.converterNotaFiscal()[0];
		
		assertEquals("H-0F75A9CD9BFDC155E050007F0100752A",nf.notaFiscalId().id());
		
		assertEquals(new Dinheiro(376.8),nf.totalProdutos());
		assertEquals(new Dinheiro(382.50),nf.total());
		
		assertEquals(new Dinheiro(146.49),nf.totalBaseCalculoIcms());
		assertEquals(new Dinheiro(26.37),nf.totalIcms());
		assertEquals(new Dinheiro(178.16),nf.totalBaseCalculoIcmsSt());
		assertEquals(new Dinheiro(5.7),nf.totalIcmsSt());
	}
		
}
