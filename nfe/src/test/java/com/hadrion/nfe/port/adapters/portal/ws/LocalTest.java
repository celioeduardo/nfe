package com.hadrion.nfe.port.adapters.portal.ws;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;

public class LocalTest {
	
	@Test
	public void ba(){
		assertEquals(Local.BA,Local.obter(TipoEmissao.NORMAL,Uf.BA));
		assertEquals(Local.BA,Local.obter(TipoEmissao.FS_DA,Uf.BA));
		assertEquals(Local.BA,Local.obter(TipoEmissao.FS_IA,Uf.BA));
		assertEquals(Local.BA,Local.obter(TipoEmissao.DPEC,Uf.BA));
	}
	
	@Test
	public void mg(){
		assertEquals(Local.MG,Local.obter(TipoEmissao.NORMAL,Uf.MG));
		assertEquals(Local.MG,Local.obter(TipoEmissao.FS_DA,Uf.MG));
		assertEquals(Local.MG,Local.obter(TipoEmissao.FS_IA,Uf.MG));
		assertEquals(Local.MG,Local.obter(TipoEmissao.DPEC,Uf.MG));
	}
	
	@Test
	public void sp(){
		assertEquals(Local.SP,Local.obter(TipoEmissao.NORMAL,Uf.SP));
		assertEquals(Local.SP,Local.obter(TipoEmissao.FS_DA,Uf.SP));
		assertEquals(Local.SP,Local.obter(TipoEmissao.FS_IA,Uf.SP));
		assertEquals(Local.SP,Local.obter(TipoEmissao.DPEC,Uf.SP));
	}
	
	@Test
	public void svcRS(){
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.AM),Uf.AM));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.BA),Uf.BA));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.CE),Uf.CE));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.GO),Uf.GO));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.MA),Uf.MA));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.MS),Uf.MS));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.MT),Uf.MT));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.PA),Uf.PA));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.PE),Uf.PE));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.PI),Uf.PI));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.PR),Uf.PR));
	}

	@Test
	public void svcAN(){
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.AC),Uf.AC));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.AL),Uf.AL));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.AP),Uf.AP));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.DF),Uf.DF));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.ES),Uf.ES));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.MG),Uf.MG));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.PB),Uf.PB));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.RJ),Uf.RJ));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.RN),Uf.RN));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.RO),Uf.RO));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.RR),Uf.RR));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.RS),Uf.RS));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.SC),Uf.SC));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.SE),Uf.SE));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.SP),Uf.SP));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.contingenciaPelaUf(Uf.TO),Uf.TO));
	}
	
}
