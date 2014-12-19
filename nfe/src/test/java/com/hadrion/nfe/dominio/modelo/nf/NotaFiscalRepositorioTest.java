package com.hadrion.nfe.dominio.modelo.nf;

import static com.hadrion.util.DataUtil.dataHora;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.endereco.Municipio;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;

public class NotaFiscalRepositorioTest extends DominioTest {
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	@Autowired
	private EntityManager em;
	
	private NotaFiscal nf;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		nf = NotaFiscalFixture.nfEmHomologacao();
	}
	
	@Test
	public void salvar () throws SQLException {
		
		notaFiscalRepositorio.salvar(nf);
		
		em.flush();
		em.clear();
		
		nf = notaFiscalRepositorio.notaFiscalPeloId(nf.notaFiscalId());
		
		assertEquals(Ambiente.HOMOLOGACAO, nf.ambiente());
		assertEquals(new FilialId("4007474000116"), nf.filialId());
		assertEquals("VENDA DE PRODUTOS ADQ. TERCEIROS", nf.naturezaOperacao());
		assertEquals(FormaPagamento.A_PRAZO, nf.formaPagamento());
		assertEquals(new Modelo("55"), nf.modelo());
		assertEquals(new Serie(1L), nf.serie());
		assertEquals(new Long(19936), nf.numero()); 
		assertEquals(dataHora("25/10/2013 00:00:00","GMT-02:00"), nf.emissao());
		assertEquals(dataHora("25/10/2013 00:00:00","GMT-02:00"), nf.dataHora());
		assertEquals(269918,nf.codigoNumerico());
		assertEquals(FormatoDanfe.RETRATO,nf.formatoDanfe());
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
		assertEquals(TipoOperacao.SAIDA,nf.tipoOperacao()); 
		assertEquals(LocalDestino.INTERNA,nf.localDestino()); 
		assertEquals(new Municipio(3111606, "CAMPOS GERAIS", Uf.MG),nf.municipioFatoGerador()); 
		assertFalse(nf.consumidorFinal());
		assertEquals(Finalidade.NORMAL,nf.finalidade()); 
		assertEquals(Presenca.OUTROS,nf.presenca()); 
		assertEquals(Processo.APLICATIVO_CONTRIBUINTE,nf.processo());
		assertFalse(nf.danfeImpresso());
		
		assertEquals(new FilialId("4007474000116"),nf.filialId());
		
		assertEquals(NotaFiscalFixture.emitente(),nf.emitente());
		assertEquals(NotaFiscalFixture.destinatario(),nf.destinatario());
		assertEquals(NotaFiscalFixture.localEntrega(),nf.localEntrega());
		assertEquals(NotaFiscalFixture.localRetirada(),nf.localRetirada());
		assertEquals(NotaFiscalFixture.transporte(),nf.transporte());
		assertEquals(NotaFiscalFixture.cobranca(),nf.cobranca());
		assertEquals(NotaFiscalFixture.exportacao(),nf.exportacao());
		assertEquals(NotaFiscalFixture.contingencia(),nf.contingencia());
		assertEquals(NotaFiscalFixture.referencias(),nf.referencias());
		assertEquals(NotaFiscalFixture.informacaoFisco(),nf.informacaoFisco());
		assertEquals(NotaFiscalFixture.informacaoContribuinte(),nf.informacaoContribuinte());
		assertEquals(NotaFiscalFixture.itens(),nf.itens());
		assertEquals(NotaFiscalFixture.itens().get(0),nf.item(0));
		
	} 
	
		
}
