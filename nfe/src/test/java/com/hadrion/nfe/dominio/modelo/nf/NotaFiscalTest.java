package com.hadrion.nfe.dominio.modelo.nf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;

public class NotaFiscalTest {
	
	private NotaFiscal nf;
	
	@Before
	public void setUp(){
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
		
		nf.alterarTipoEmissao(TipoEmissao.FS_DA);
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
		
		nf.alterarTipoEmissao(TipoEmissao.NORMAL);
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
	} 
	
	@Test
	public void trocaTipoEmissaoDeNormalParaFsiaPermitida(){
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
		
		nf.alterarTipoEmissao(TipoEmissao.FS_IA);
		assertEquals(TipoEmissao.FS_IA,nf.tipoEmissao());
		
		nf.alterarTipoEmissao(TipoEmissao.NORMAL);
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaContingencia();
		assertTrue(nf.tipoEmissao().contingencia());
	}
	
	@Test
	public void trocaTipoEmissaoDeNormalParaContingenciaPermitida(){
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaContingencia();
		assertTrue(nf.tipoEmissao().contingencia());
		
		nf.alterarTipoEmissao(TipoEmissao.NORMAL);
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
	}

	@Test
	public void trocaTipoEmissaoEntreFsda_e_FsiaPermitida(){
		nf.alterarTipoEmissao(TipoEmissao.FS_DA);
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
		
		nf.alterarTipoEmissao(TipoEmissao.FS_IA);
		assertEquals(TipoEmissao.FS_IA,nf.tipoEmissao());
		
		nf.alterarTipoEmissao(TipoEmissao.FS_DA);
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
	}
	
	@Test
	public void trocaTipoEmissaoEntreFsda_e_ContingenciaPermitida(){
		nf.alterarTipoEmissao(TipoEmissao.FS_DA);
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaContingencia();
		assertTrue(nf.tipoEmissao().contingencia());
		
		nf.alterarTipoEmissao(TipoEmissao.FS_DA);
		assertEquals(TipoEmissao.FS_DA,nf.tipoEmissao());
	}
	
	@Test
	public void trocaTipoEmissaoEntreFsia_e_ContingenciaPermitida(){
		nf.alterarTipoEmissao(TipoEmissao.FS_IA);
		assertEquals(TipoEmissao.FS_IA,nf.tipoEmissao());
		
		nf.alterarTipoEmissaoParaContingencia();
		assertTrue(nf.tipoEmissao().contingencia());
		
		nf.alterarTipoEmissao(TipoEmissao.FS_IA);
		assertEquals(TipoEmissao.FS_IA,nf.tipoEmissao());
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeNormalParaFsdaNaoPermitida(){
		nf = NotaFiscalFixture.nfEmProducaoAutorizada();
		nf.alterarTipoEmissao(TipoEmissao.FS_DA);
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeNormalParaFsiaNaoPermitida(){
		nf = NotaFiscalFixture.nfEmProducaoAutorizada();
		nf.alterarTipoEmissao(TipoEmissao.FS_IA);
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeNormalParaContingenciaNaoPermitida(){
		nf = NotaFiscalFixture.nfEmProducaoAutorizada();
		nf.alterarTipoEmissaoParaContingencia();
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeFsdaParaOutroTipoNaoPermitidoSeFormularioSegurancaFoiImpresso(){
		nf = NotaFiscalFixture.nfEmProducao();
		nf.alterarTipoEmissao(TipoEmissao.FS_DA);
		nf.definirDanfeComoImpresso();
		nf.alterarTipoEmissao(TipoEmissao.NORMAL);
	}
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeFsiaParaOutroTipoNaoPermitidoSeFormularioSegurancaFoiImpresso(){
		nf = NotaFiscalFixture.nfEmProducao();
		nf.alterarTipoEmissao(TipoEmissao.FS_IA);
		nf.definirDanfeComoImpresso();
		nf.alterarTipoEmissao(TipoEmissao.NORMAL);
	}
	
	@Test
	public void definirFormularioDeSegurancaFsdaComoImpresso(){
		nf = NotaFiscalFixture.nfEmProducao();
		nf.alterarTipoEmissao(TipoEmissao.FS_DA);
		nf.definirDanfeComoImpresso();
		assertTrue(nf.danfeImpresso());
	}
	
	@Test
	public void definirFormularioDeSegurancaFsiaComoImpresso(){
		nf = NotaFiscalFixture.nfEmProducao();
		nf.alterarTipoEmissao(TipoEmissao.FS_IA);
		nf.definirDanfeComoImpresso();
		assertTrue(nf.danfeImpresso());
	}
	
}
