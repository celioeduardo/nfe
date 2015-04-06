package com.hadrion.nfe.port.adapters.portal.ws;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;

public class LocalEndpointsTest {
	
	@Test
	public void autorizacao(){
		//PRODUÇÃO
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfeautorizacao.asmx",
				Local.SP.endpointAutorizacao(Ambiente.PRODUCAO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/NfeAutorizacao",
				Local.MG.endpointAutorizacao(Ambiente.PRODUCAO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/NfeAutorizacao/NfeAutorizacao.asmx",
				Local.BA.endpointAutorizacao(Ambiente.PRODUCAO));
		assertEquals("https://www.svc.fazenda.gov.br/NfeAutorizacao/NfeAutorizacao.asmx",
				Local.SVC_AN.endpointAutorizacao(Ambiente.PRODUCAO));
		assertEquals("https://nfe.sefazvirtual.rs.gov.br/ws/NfeAutorizacao/NFeAutorizacao.asmx",
				Local.SVC_RS.endpointAutorizacao(Ambiente.PRODUCAO));
		//HOMOLOGAÇÃO
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeautorizacao.asmx",
				Local.SP.endpointAutorizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeAutorizacao",
				Local.MG.endpointAutorizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/NfeAutorizacao/NfeAutorizacao.asmx",
				Local.BA.endpointAutorizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://hom.svc.fazenda.gov.br/NfeAutorizacao/NfeAutorizacao.asmx",
				Local.SVC_AN.endpointAutorizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeAutorizacao/NFeAutorizacao.asmx",
				Local.SVC_RS.endpointAutorizacao(Ambiente.HOMOLOGACAO));
	}
	
	@Test
	public void retornoAutorizacao(){
		//PRODUÇÃO
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nferetautorizacao.asmx",
				Local.SP.endpointRetornoAutorizacao(Ambiente.PRODUCAO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/NfeRetAutorizacao",
				Local.MG.endpointRetornoAutorizacao(Ambiente.PRODUCAO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/NfeRetAutorizacao/NfeRetAutorizacao.asmx",
				Local.BA.endpointRetornoAutorizacao(Ambiente.PRODUCAO));
		assertEquals("https://www.svc.fazenda.gov.br/NfeRetAutorizacao/NfeRetAutorizacao.asmx",
				Local.SVC_AN.endpointRetornoAutorizacao(Ambiente.PRODUCAO));
		assertEquals("https://nfe.sefazvirtual.rs.gov.br/ws/NfeRetAutorizacao/NFeRetAutorizacao.asmx",
				Local.SVC_RS.endpointRetornoAutorizacao(Ambiente.PRODUCAO));
		//HOMOLOGAÇÃO
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nferetautorizacao.asmx",
				Local.SP.endpointRetornoAutorizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeRetAutorizacao",
				Local.MG.endpointRetornoAutorizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/NfeRetAutorizacao/NfeRetAutorizacao.asmx",
				Local.BA.endpointRetornoAutorizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://hom.svc.fazenda.gov.br/NfeRetAutorizacao/NfeRetAutorizacao.asmx",
				Local.SVC_AN.endpointRetornoAutorizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeRetAutorizacao/NFeRetAutorizacao.asmx",
				Local.SVC_RS.endpointRetornoAutorizacao(Ambiente.HOMOLOGACAO));
	}
	
	@Test
	public void evento(){
		//PRODUÇÃO
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/recepcaoevento.asmx",
				Local.SP.endpointEvento(Ambiente.PRODUCAO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/RecepcaoEvento",
				Local.MG.endpointEvento(Ambiente.PRODUCAO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/sre/recepcaoevento.asmx",
				Local.BA.endpointEvento(Ambiente.PRODUCAO));
		assertEquals("https://www.svc.fazenda.gov.br/RecepcaoEvento/RecepcaoEvento.asmx",
				Local.SVC_AN.endpointEvento(Ambiente.PRODUCAO));
		assertEquals("https://nfe.sefazvirtual.rs.gov.br/ws/recepcaoevento/recepcaoevento.asmx",
				Local.SVC_RS.endpointEvento(Ambiente.PRODUCAO));
		//HOMOLOGAÇÃO
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/recepcaoevento.asmx",				
				Local.SP.endpointEvento(Ambiente.HOMOLOGACAO));
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/RecepcaoEvento",
				Local.MG.endpointEvento(Ambiente.HOMOLOGACAO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/sre/recepcaoevento.asmx",
				Local.BA.endpointEvento(Ambiente.HOMOLOGACAO));		
		assertEquals("https://hom.svc.fazenda.gov.br/RecepcaoEvento/RecepcaoEvento.asmx",
				Local.SVC_AN.endpointEvento(Ambiente.HOMOLOGACAO));		
		assertEquals("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/recepcaoevento/recepcaoevento.asmx",
				Local.SVC_RS.endpointEvento(Ambiente.HOMOLOGACAO));
	}
	
	@Test
	public void inutilizacao(){
		//PRODUÇÃO
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfeinutilizacao2.asmx",
				Local.SP.endpointInutilizacao(Ambiente.PRODUCAO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/NfeInutilizacao2",
				Local.MG.endpointInutilizacao(Ambiente.PRODUCAO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/NfeInutilizacao/NfeInutilizacao.asmx",
				Local.BA.endpointInutilizacao(Ambiente.PRODUCAO));
		assertEquals("https://www.sefazvirtual.fazenda.gov.br/NfeInutilizacao2/NfeInutilizacao2.asmx",
				Local.SVC_AN.endpointInutilizacao(Ambiente.PRODUCAO));
		assertEquals("https://nfe.sefazvirtual.rs.gov.br/ws/nfeinutilizacao/nfeinutilizacao2.asmx",
				Local.SVC_RS.endpointInutilizacao(Ambiente.PRODUCAO));
		//HOMOLOGAÇÃO
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeinutilizacao2.asmx",
				Local.SP.endpointInutilizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeInutilizacao2",
				Local.MG.endpointInutilizacao(Ambiente.HOMOLOGACAO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/NfeInutilizacao/NfeInutilizacao.asmx",
				Local.BA.endpointInutilizacao(Ambiente.HOMOLOGACAO));
	}
}
