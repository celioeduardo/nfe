package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.*;

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
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.NotaFiscalTradutorJson;

public class NotaFiscalTradutorJsonTest {

	@Test
	public void traduzirNota() throws IOException{
		
		final File json =FileUtils.getFile("src","test", "resources", "nota.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(FileUtils.readFileToString(json));
		NotaFiscal nf = tradutor.converterNotaFiscal();
		
		assertEquals("013924F5867C4CC4E050007F010060FB",nf.notaFiscalId().id());
		assertEquals(new Long(204755),nf.numero());
		assertEquals(new Serie(2L),nf.serie());
		assertEquals(new Modelo("55"),nf.modelo());
		assertEquals(data("10/06/2014"),nf.emissao());
		assertEquals(data("10/06/2014"),nf.dataHora());
		assertEquals(TipoOperacao.SAIDA,nf.tipoOperacao());
		assertEquals(LocalDestino.INTERESTADUAL,nf.localDestino());
		assertEquals(Finalidade.NORMAL, nf.finalidade());
		assertEquals(0,nf.getReferencias().size());
		assertFalse(nf.estaReferenciando(
				Referencia.nfe(new ChaveAcesso("29140600891206000310550010000110017000481161"))));
		
		assertFalse(nf.consumidorFinal());
		
		assertEquals(3, nf.itens().size());
		assertEquals(new DescritorProduto("938","CENOURA 3A"),				
				nf.item(0).produto());
		
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
