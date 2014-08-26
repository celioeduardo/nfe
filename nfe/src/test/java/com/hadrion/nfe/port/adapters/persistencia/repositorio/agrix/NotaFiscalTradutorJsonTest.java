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

import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.NotaFiscalTradutorJson;

public class NotaFiscalTradutorJsonTest {
	
	@Test
	public void traduzirNota() throws IOException{
		
		final File json =FileUtils.getFile("src","test", "resources", "nota.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(FileUtils.readFileToString(json));
		NotaFiscal nf = tradutor.converterNotaFiscal();
		
		assertEquals("013924F586684CC4E050007F010060FB",nf.notaFiscalId().id());
		assertEquals(new Long(204802),nf.numero());
		assertEquals(new Serie(2L),nf.serie());
		assertEquals(new Modelo("55"),nf.modelo());
		assertEquals(data("19/08/2014"),nf.emissao());
		assertEquals(data("19/08/2014"),nf.dataHora());
		assertEquals(TipoOperacao.SAIDA,nf.tipoOperacao());
		assertEquals(LocalDestino.INTERNA,nf.localDestino());
		assertEquals(Finalidade.NORMAL, nf.finalidade());
		assertEquals(2, nf.itens().size());
		assertEquals(new DescritorProduto("1012","ALHO COOPADAP ESPECIAL 04 (NOBILISSIMO)"),
				nf.item(0).produto());
		
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
	@SuppressWarnings("unused")
	private Date dataHota(String dataHora){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		try {
			return formatter.parse(dataHora);
		} catch (ParseException e) {
			return null;
		}	
		
	}
}
