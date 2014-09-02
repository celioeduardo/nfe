package com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.endereco.Cep;
import com.hadrion.nfe.dominio.modelo.endereco.Endereco;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.endereco.Pais;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Finalidade;
import com.hadrion.nfe.dominio.modelo.nf.LocalDestino;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.Referencia;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoOperacao;
import com.hadrion.nfe.dominio.modelo.nf.item.DescritorProduto;
import com.hadrion.nfe.dominio.modelo.nf.publico.Emitente;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.port.adapters.persistencia.repositorio.agrix.json.NotaFiscalTradutorJson;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.nfe.tipos.InscricaoEstadual;
import com.hadrion.nfe.tipos.Telefone;

public class NotaFiscalTradutorJsonTest {

	@Test
	public void traduzirNota() throws IOException{
		
		final File json =FileUtils.getFile("src","test", "resources", "nota.json");
		
		NotaFiscalTradutorJson tradutor = new NotaFiscalTradutorJson(FileUtils.readFileToString(json));
		NotaFiscal nf = tradutor.converterNotaFiscal();
		
		assertEquals("013924F30E424CC4E050007F010060FB",nf.notaFiscalId().id());
		assertEquals(new Long(618),nf.numero());
		assertEquals(new Serie(2L),nf.serie());
		assertEquals(new Modelo("55"),nf.modelo());
		assertEquals(data("28/09/2009"),nf.emissao());
		assertEquals(data("28/09/2009"),nf.dataHora());
		assertEquals(TipoOperacao.SAIDA,nf.tipoOperacao());
		assertEquals(LocalDestino.INTERNA,nf.localDestino());
		assertEquals(Finalidade.NORMAL, nf.finalidade());
		assertEquals(1,nf.getReferencias().size());
		assertTrue(nf.estaReferenciando(
				Referencia.nfe(new ChaveAcesso("013924F307774CC4E050007F010060FB"))));		
		assertFalse(nf.consumidorFinal());		
		assertEquals(4, nf.itens().size());
		assertEquals(new DescritorProduto("9001","MILHO"),				
				nf.item(0).produto());
		assertEquals(new DescritorProduto("8452","LENHA"),				
				nf.item(3).produto());
		
		assertEquals(new Emitente(
						new Cnpj(7233848000100L), 
						null, 
						"OSPER AGROINDUSTRIAL S/A", 
						"", 
						new Endereco("ROD. BR 262 KM 443 S/N ", 
								"S/N",
								"",
								"ZONA RURAL",
							    new Municipio("NOVA SERRANA - MG",Uf.MG),
							    new Pais(1L,"BRASIL"),
							    new Cep(35519000L)),
						new Telefone("3732322434"), 
						new InscricaoEstadual("452332065.00-50"), 
						new InscricaoEstadual("452332065.00-50")), nf.emitente());
		
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
