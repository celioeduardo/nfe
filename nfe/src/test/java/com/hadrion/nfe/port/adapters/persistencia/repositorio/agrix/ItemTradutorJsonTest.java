package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.item.Item;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.NotaFiscalTradutorJson;

public class ItemTradutorJsonTest {
	
	@Test
	public void traduzirItem() throws IOException{
		
		final File json =FileUtils.getFile("src","test", "resources", "item.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(FileUtils.readFileToString(json));
		Item item = tradutor.converterItem();
		//assertEquals("013924F586684CC4E050007F010060FB",item.);
		//assertEquals("013924F586684CC4E050007F010060FB",new Item
		assertEquals("1012",item.produto().codigo());
		assertEquals("ALHO COOPADAP ESPECIAL 04 (NOBILISSIMO)",item.produto().descricao());
		
		System.out.println(FileUtils.readFileToString(json));
	}

}
