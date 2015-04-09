package com.hadrion.nfe.port.adapters.portal.inutilizacao;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.DominioTest;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.dominio.modelo.inutilizacao.InutilizacaoId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.InutilizacaoPortalService;
import com.hadrion.nfe.dominio.modelo.portal.inutilizacao.RetornoInutilizacao;
import com.hadrion.nfe.port.adapters.portal.ws.Local;
import com.hadrion.nfe.tipos.Cnpj;

public class SoapInutilizacaoServiceAdapterTest extends DominioTest{
	
	@Autowired
	private InutilizacaoPortalService inutilizacaoPortalService;
	
	private Certificado certificado;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
	}
	
	@Test @Ignore
	public void inutilizar() throws Throwable{
		
		Inutilizacao inutilizacao = new Inutilizacao(
				new InutilizacaoId("123456"),
				Ambiente.HOMOLOGACAO,
				new Serie(1),
				101,101,
				"Justificativa de inutilização",
				new FilialId("53-86675642000106"));
		
		certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado.pfx"), 
				"12345678"); 
		
		RetornoInutilizacao retorno = inutilizacaoPortalService.inutilizar(
				inutilizacao, certificado, Local.MG,Uf.MG,new Cnpj(86675642000106L));
		
		//System.out.println(retorno.xmlRetorno());
		System.out.println("\nNúmero do Recibo retornado: "+retorno.numeroProtocolo());
	}
	
	@Test @Ignore
	public void inutilizarEmSp() throws Throwable{
		
		Inutilizacao inutilizacao = new Inutilizacao(
				new InutilizacaoId("14508"),
				Ambiente.PRODUCAO,
				new Serie(1),
				101,101,
				"FALHA NA SEQUENCIA DA NUMERACAO",
				new FilialId("1-3754929000102"));
		
		certificado = new Certificado(
				FileUtils.getFile("src","test","resources","assinatura","certificado-sp.pfx"), 
				"1234"); 
		
		RetornoInutilizacao retorno = inutilizacaoPortalService.inutilizar(
				inutilizacao, certificado, Local.SP,Uf.SP,new Cnpj(3754929000102L));
		
		System.out.println(retorno.xmlRetorno());
		System.out.println("\nNúmero do Recibo retornado: "+retorno.numeroProtocolo());
	}
}
