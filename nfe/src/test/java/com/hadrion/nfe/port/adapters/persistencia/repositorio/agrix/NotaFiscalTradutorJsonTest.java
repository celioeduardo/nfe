package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.NotaFiscalTradutorJson;

public class NotaFiscalTradutorJsonTest {
	
	@Test
	public void traduzirNota() throws IOException{
		final File json =FileUtils.getFile("src","test", "resources", "nota.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(FileUtils.readFileToString(json));
		NotaFiscal nf = tradutor.converter();
		assertEquals(new Long(204802),nf.numero());
		assertEquals(new Serie(2L),nf.serie());
		assertEquals(new Modelo("55"),nf.modelo());
		assertEquals(TipoOperacao.SAIDA,nf.tipoOperacao());
		assertEquals(LocalDestino.INTERNA,nf.localDestino());
		assertEquals(data("19/08/2014"),nf.emissao());
		assertFalse(nf.consumidorFinal());
		
		System.out.println(FileUtils.readFileToString(json));
	}
	private Date data(String data){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			return formatter.parse(data);
		} catch (ParseException e) {
			return null;
		}	
		
	}

}
