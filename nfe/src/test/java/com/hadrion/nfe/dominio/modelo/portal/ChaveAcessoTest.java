package com.hadrion.nfe.dominio.modelo.portal;

import static com.hadrion.util.DataUtil.outubro;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.Modelo;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;
import com.hadrion.nfe.tipos.Cnpj;

public class ChaveAcessoTest {
	
	
	@Test
	public void nova(){
		
		ChaveAcesso chave = new ChaveAcesso(
				Uf.MG,
				outubro(25,2013),
				new Cnpj(16832651000420L),
				new Serie(1),
				19936L,
				TipoEmissao.NORMAL,
				269918);
		
		assertEquals(new ChaveAcesso("31131016832651000420550010000199361002699180"),chave);
		
		assertEquals(Uf.MG,chave.uf());
		assertEquals("1310",chave.anoMes());
		assertEquals(new Cnpj(16832651000420L),chave.cnpj());
		assertEquals(new Modelo("55"),chave.modelo());
		assertEquals(new Serie(1),chave.serie());
		assertEquals(new Long(19936),chave.numero());
		assertEquals(TipoEmissao.NORMAL,chave.tipoEmissao());
		assertEquals(269918, chave.codigo());
		assertEquals(0, chave.digitoVerificador());
	}
	
	@Test
	public void novaComoString(){
		
		ChaveAcesso chave = new ChaveAcesso("31131016832651000420550010000199361002699180");
		
		assertEquals(Uf.MG,chave.uf());
		assertEquals("1310",chave.anoMes());
		assertEquals(new Cnpj(16832651000420L),chave.cnpj());
		assertEquals(new Modelo("55"),chave.modelo());
		assertEquals(new Serie(1),chave.serie());
		assertEquals(new Long(19936),chave.numero());
		assertEquals(TipoEmissao.NORMAL,chave.tipoEmissao());
		assertEquals(269918, chave.codigo());
		assertEquals(0, chave.digitoVerificador());
	}
	
	@Test
	public void chavesDiferentesParaMesmoDocumento(){

		ChaveAcesso chave1 = new ChaveAcesso(
				Uf.MG,
				outubro(25,2013),
				new Cnpj(16832651000420L),
				new Serie(1),
				19936L,
				TipoEmissao.NORMAL,
				111111);
		

		ChaveAcesso chave2 = new ChaveAcesso(
				Uf.MG,
				outubro(25,2013),
				new Cnpj(16832651000420L),
				new Serie(1),
				19936L,
				TipoEmissao.NORMAL,
				222222);
		
		assertTrue(chave1.igualChaveDocumento(chave2));
		
	}
}
