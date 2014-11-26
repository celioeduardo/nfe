package com.hadrion.nfe.dominio.modelo.nf;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;

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
				new NotaFiscalId("123456"));
		
		assertEquals(Ambiente.PRODUCAO,nf.ambiente());
	}
	
	@Test
	public void novaNotaEmHomologacao(){
		NotaFiscal nf = new NotaFiscal(
				Ambiente.HOMOLOGACAO,
				new NotaFiscalId("123456"));
		
		assertEquals(Ambiente.HOMOLOGACAO,nf.ambiente());
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
	
	@Test
	public void trocaTipoEmissaoDeNormalParaContingencia(){
		assertEquals(TipoEmissao.NORMAL,nf.tipoEmissao());
		nf.alterarTipoEmissaoParaContingencia();
		assertTrue(nf.tipoEmissao().contingencia());
	} 
	
	@Test(expected=RuntimeException.class)
	public void trocaTipoEmissaoDeNormalParaContingenciaNaoPermitida(){
		nf = NotaFiscalFixture.nfEmProducaoAutorizada();
		nf.alterarTipoEmissaoParaContingencia();
	}
	
	//TODO NÃO PERMITIR troca do Tipo de Emissão quando houver Formulário de Segurança (FS-DA ou FS-IA) Impresso 
	
	
}
