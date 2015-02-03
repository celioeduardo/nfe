package com.hadrion.nfe.dominio.modelo.nf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;

public class NotaFiscalTest extends DominioTest{
	
	private NotaFiscal nf;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		nf = NotaFiscalFixture.nfEmProducao();
	}
	
	@Test
	public void novaNotaEmProducao(){
		NotaFiscal nf = new NotaFiscal(
				Ambiente.PRODUCAO,
				new NotaFiscalId("123456"),
				new FilialId("4007474000116"));
		
		assertEquals(Ambiente.PRODUCAO,nf.ambiente());
		assertEquals(new FilialId("4007474000116"),nf.filialId());
	}
	
	@Test
	public void novaNotaEmHomologacao(){
		NotaFiscal nf = new NotaFiscal(
				Ambiente.HOMOLOGACAO,
				new NotaFiscalId("123456"),
				new FilialId("4007474000116"));
		
		assertEquals(Ambiente.HOMOLOGACAO,nf.ambiente());
		assertEquals(new FilialId("4007474000116"),nf.filialId());
	}
	
	@Test
	public void trocaTipoEmissaoDeNormalParaFsdaPermitida(){
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaFsDa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaNormal();
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
	} 
	
	@Test
	public void trocaTipoEmissaoDeNormalParaFsiaPermitida(){
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaFsIa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		assertEquals(TipoEmissao.FS_IA,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaNormal();
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaContingencia(new Contingencia(new Date(), "Servidor da SEFAZ fora do ar"));
		assertTrue(nf.tipoEmissao().contingencia());
	}
	
	@Test
	public void trocaTipoEmissaoDeNormalParaContingenciaPermitida(){
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaContingencia(new Contingencia(new Date(), "Servidor da SEFAZ fora do ar"));
		assertTrue(nf.tipoEmissao().contingencia());
		
		nf.alterarTipoEmissaoParaNormal();
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
	}

	@Test
	public void trocaTipoEmissaoEntreFsda_e_FsiaPermitida(){
		nf.alterarTipoEmissaoParaFsDa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaFsIa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		assertEquals(TipoEmissao.FS_IA,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaFsDa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
	}
	
	@Test
	public void trocaTipoEmissaoEntreFsda_e_ContingenciaPermitida(){
		nf.alterarTipoEmissaoParaFsDa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaContingencia(new Contingencia(new Date(), "Servidor da SEFAZ fora do ar"));
		assertTrue(nf.tipoEmissao().contingencia());
		
		nf.alterarTipoEmissaoParaFsDa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
	}
	
	@Test
	public void trocaTipoEmissaoEntreFsia_e_ContingenciaPermitida(){
		nf.alterarTipoEmissaoParaFsIa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		assertEquals(TipoEmissao.FS_IA,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaContingencia(new Contingencia(new Date(), "Servidor da SEFAZ fora do ar"));
		assertTrue(nf.tipoEmissao().contingencia());
		
		nf.alterarTipoEmissaoParaFsIa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		assertEquals(TipoEmissao.FS_IA,nf.tipoEmissao());
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeNormalParaFsdaNaoPermitida(){
		nf = NotaFiscalFixture.nfEmProducaoAutorizada();
		nf.alterarTipoEmissaoParaFsDa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeNormalParaFsiaNaoPermitida(){
		nf = NotaFiscalFixture.nfEmProducaoAutorizada();
		nf.alterarTipoEmissaoParaFsIa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeNormalParaContingenciaNaoPermitida(){
		nf = NotaFiscalFixture.nfEmProducaoAutorizada();
		nf.alterarTipoEmissaoParaContingencia(new Contingencia(new Date(), "Servidor da SEFAZ fora do ar"));
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeFsdaParaOutroTipoNaoPermitidoSeFormularioSegurancaFoiImpresso(){
		nf = NotaFiscalFixture.nfEmProducao();
		nf.alterarTipoEmissaoParaFsDa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		nf.definirDanfeComoImpresso();
		nf.alterarTipoEmissaoParaNormal();
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeFsiaParaOutroTipoNaoPermitidoSeFormularioSegurancaFoiImpresso(){
		nf = NotaFiscalFixture.nfEmProducao();
		nf.alterarTipoEmissaoParaFsIa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		nf.definirDanfeComoImpresso();
		nf.alterarTipoEmissaoParaNormal();
	}
	
	@Test
	public void definirFormularioDeSegurancaFsdaComoImpresso(){
		nf = NotaFiscalFixture.nfEmProducao();
		nf.alterarTipoEmissaoParaFsDa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		nf.definirDanfeComoImpresso();
		assertTrue(nf.danfeImpresso());
	}
	
	@Test
	public void definirFormularioDeSegurancaFsiaComoImpresso(){
		nf = NotaFiscalFixture.nfEmProducao();
		nf.alterarTipoEmissaoParaFsIa(new Contingencia(new Date(), "Conexão com a Internet com problema"));
		nf.definirDanfeComoImpresso();
		assertTrue(nf.danfeImpresso());
	}
	
	@Test(expected=RuntimeException.class)
	public void naoPermitirImprimirDanfeParaNotaNaoAutorizada(){
		nf = NotaFiscalFixture.nfEmProducao();
		nf.definirDanfeComoImpresso();
	}
	
}
