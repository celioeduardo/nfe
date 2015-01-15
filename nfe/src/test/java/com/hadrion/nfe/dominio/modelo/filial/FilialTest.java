package com.hadrion.nfe.dominio.modelo.filial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Profile;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.nf.Contingencia;
import com.hadrion.nfe.tipos.Cnpj;
import com.hadrion.util.DataUtil;

@Profile("test")
public class FilialTest extends DominioTest{
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
	}
	
	
	@Test
	public void novaFilial(){
		
		Filial filial = new Filial(
				new FilialId("654321"),
				"Hadrion",
				new Cnpj(4007474000116L), 
				new EmpresaId("123456"),
				Ambiente.HOMOLOGACAO);
		
		assertEquals(new FilialId("654321"),filial.filialId());
		assertEquals("Hadrion",filial.nome());
		assertEquals(new Cnpj(4007474000116L),filial.cnpj());
		assertEquals(new EmpresaId("123456"),filial.empresaId());
		assertEquals(Ambiente.HOMOLOGACAO,filial.ambiente());
	}
	
	@Test
	public void renomear(){
		Filial filial = new Filial(
				new FilialId("654321"),
				"Hadrion",
				new Cnpj(4007474000116L), 
				new EmpresaId("123456"),
				Ambiente.HOMOLOGACAO);
		
		filial.renomear("Hadrion [ALTERADO]");
		assertEquals("Hadrion [ALTERADO]",filial.nome());
	}
	
	@Test
	public void alterarAmbiente(){
		Filial filial = filialFixture();
		
		assertEquals(Ambiente.HOMOLOGACAO,filial.ambiente());
		
		filial.operarEmProducao();
		assertEquals(Ambiente.PRODUCAO,filial.ambiente());
		
		filial.operarEmHomologacao();
		assertEquals(Ambiente.HOMOLOGACAO,filial.ambiente());
	}
	
	@Test
	public void alterarModoOperacao(){
		Filial filial = filialFixture();
		
		assertEquals(ModoOperacao.NORMAL, filial.modoOperacao());
		
		filial.operarEmFsDa(new Contingencia(DataUtil.dataHota("12/01/2015 08:54:00"), "Conexão com a Internet com problema"));
		assertEquals(ModoOperacao.FS_DA, filial.modoOperacao());
		assertEquals(new Contingencia(DataUtil.dataHota("12/01/2015 08:54:00"), "Conexão com a Internet com problema"), 
				filial.contingencia());
		
		filial.operarEmSvc(new Contingencia(DataUtil.dataHota("12/01/2015 08:54:00"), "Servidor da SEFAZ fora do ar"));
		assertEquals(ModoOperacao.SVC, filial.modoOperacao());
		assertEquals(new Contingencia(DataUtil.dataHota("12/01/2015 08:54:00"), "Servidor da SEFAZ fora do ar"), 
				filial.contingencia());
		
		filial.operarEmModoNormal();
		assertEquals(ModoOperacao.NORMAL, filial.modoOperacao());
		assertNull(filial.contingencia());
		
		
	}
	
	private Filial filialFixture(){
		return new Filial(
				new FilialId("654321"),
				"Hadrion",
				new Cnpj(4007474000116L), 
				new EmpresaId("123456"),
				Ambiente.HOMOLOGACAO);
	}
	
}
