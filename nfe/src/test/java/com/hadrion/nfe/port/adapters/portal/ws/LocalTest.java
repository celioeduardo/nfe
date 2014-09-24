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
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.AM));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.BA));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.CE));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.GO));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.MA));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.MS));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.MT));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.PA));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.PE));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.PI));
		assertEquals(Local.SVC_RS,Local.obter(TipoEmissao.SVC_RS,Uf.PR));
	}

	@Test
	public void svcAN(){
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.AC));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.AL));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.AP));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.DF));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.ES));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.MG));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.PB));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.RJ));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.RN));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.RO));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.RR));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.RS));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.SC));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.SE));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.SP));
		assertEquals(Local.SVC_AN,Local.obter(TipoEmissao.SVC_AN,Uf.TO));
	}
	
}
