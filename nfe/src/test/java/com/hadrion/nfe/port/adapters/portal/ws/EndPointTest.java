package com.hadrion.nfe.port.adapters.portal.ws;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;

public class EndPointTest {
	
	//TODO Testar outros EndPoints
	
	@Test
	public void sp(){
		Local local = Local.SP;
		Versao versao = Versao.V3_10;
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfeautorizacao.asmx",producao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nferetautorizacao.asmx",producao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/recepcaoevento.asmx",producao(local,versao,Servico.EVENTO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/cadconsultacadastro2.asmx",producao(local,versao,Servico.CONSULTA_CADASTRO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfestatusservico2.asmx",producao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfeconsulta2.asmx",producao(local,versao,Servico.CONSULTA_PROTOCOLO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfeinutilizacao2.asmx",producao(local,versao,Servico.INUTILIZACAO));
		//Homologação
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeautorizacao.asmx",homologacao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nferetautorizacao.asmx",homologacao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/recepcaoevento.asmx",homologacao(local,versao,Servico.EVENTO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/cadconsultacadastro2.asmx",homologacao(local,versao,Servico.CONSULTA_CADASTRO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfestatusservico2.asmx",homologacao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeconsulta2.asmx",homologacao(local,versao,Servico.CONSULTA_PROTOCOLO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeinutilizacao2.asmx",homologacao(local,versao,Servico.INUTILIZACAO));
	}
	
	
	@Test
	public void igualdade(){
		assertEquals(
				new EndPoint(Ambiente.HOMOLOGACAO,Local.SP,Versao.V3_10,Servico.AUTORIZACAO),
				new EndPoint(Ambiente.HOMOLOGACAO,Local.SP,Versao.V3_10,Servico.AUTORIZACAO));
		
	}
	
	private String homologacao(Local local, Versao versao, Servico servico){
		return EndPoints.homologacao(local,versao,servico);
	}
	private String producao(Local local, Versao versao, Servico servico){
		return EndPoints.producao(local,versao,servico);
	}
	
}