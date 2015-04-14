package com.hadrion.nfe.port.adapters.portal.ws;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hadrion.nfe.dominio.modelo.Ambiente;

public class EndPointTest {
	
	@Test
	public void sp(){
		Local local = Local.SP;
		Versao versao = Versao.V3_10;
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfeautorizacao.asmx",producao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nferetautorizacao.asmx",producao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/recepcaoevento.asmx",producao(local,Versao.V1_00,Servico.EVENTO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/cadconsultacadastro2.asmx",producao(local,versao,Servico.CONSULTA_CADASTRO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfestatusservico2.asmx",producao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfeconsulta2.asmx",producao(local,versao,Servico.CONSULTA_PROTOCOLO));
		assertEquals("https://nfe.fazenda.sp.gov.br/ws/nfeinutilizacao2.asmx",producao(local,versao,Servico.INUTILIZACAO));
		//Homologação
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeautorizacao.asmx",homologacao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nferetautorizacao.asmx",homologacao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/recepcaoevento.asmx",homologacao(local,Versao.V1_00,Servico.EVENTO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/cadconsultacadastro2.asmx",homologacao(local,versao,Servico.CONSULTA_CADASTRO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfestatusservico2.asmx",homologacao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeconsulta2.asmx",homologacao(local,versao,Servico.CONSULTA_PROTOCOLO));
		assertEquals("https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeinutilizacao2.asmx",homologacao(local,versao,Servico.INUTILIZACAO));
	}
	@Test
	public void mg(){
		Local local = Local.MG;
		Versao versao = Versao.V3_10;
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/NfeAutorizacao",producao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/NfeRetAutorizacao",producao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/RecepcaoEvento",producao(local,Versao.V1_00,Servico.EVENTO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/cadconsultacadastro2",producao(local,Versao.V2_00,Servico.CONSULTA_CADASTRO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/NfeStatus2",producao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/NfeConsulta2",producao(local,versao,Servico.CONSULTA_PROTOCOLO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/NfeInutilizacao2",producao(local,Versao.V2_00,Servico.INUTILIZACAO));
		//Homologação
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeAutorizacao",homologacao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeRetAutorizacao",homologacao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/RecepcaoEvento",homologacao(local,Versao.V1_00,Servico.EVENTO));
		assertEquals("https://nfe.fazenda.mg.gov.br/nfe2/services/cadconsultacadastro2",homologacao(local,Versao.V2_00,Servico.CONSULTA_CADASTRO));
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeStatus2",homologacao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeConsulta2",homologacao(local,versao,Servico.CONSULTA_PROTOCOLO));
		assertEquals("https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeInutilizacao2",homologacao(local,Versao.V2_00,Servico.INUTILIZACAO));
	}
	
	@Test
	public void svcAn(){
		Local local = Local.SVC_AN;
		Versao versao = Versao.V3_10;
		assertEquals("https://www.svc.fazenda.gov.br/NfeAutorizacao/NfeAutorizacao.asmx",producao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://www.svc.fazenda.gov.br/NfeRetAutorizacao/NfeRetAutorizacao.asmx",producao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://www.svc.fazenda.gov.br/RecepcaoEvento/RecepcaoEvento.asmx",producao(local,Versao.V1_00,Servico.EVENTO));
		assertEquals("https://www.svc.fazenda.gov.br/NfeStatusServico2/NfeStatusServico2.asmx",producao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://www.svc.fazenda.gov.br/NfeConsulta2/NfeConsulta2.asmx",producao(local,versao,Servico.CONSULTA_PROTOCOLO));
		//Homologação
		assertEquals("https://hom.svc.fazenda.gov.br/NfeAutorizacao/NfeAutorizacao.asmx",homologacao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://hom.svc.fazenda.gov.br/NfeRetAutorizacao/NfeRetAutorizacao.asmx",homologacao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://hom.svc.fazenda.gov.br/RecepcaoEvento/RecepcaoEvento.asmx",homologacao(local,Versao.V1_00,Servico.EVENTO));
		assertEquals("https://hom.svc.fazenda.gov.br/NfeStatusServico2/NfeStatusServico2.asmx",homologacao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://hom.svc.fazenda.gov.br/NfeConsulta2/NfeConsulta2.asmx",homologacao(local,versao,Servico.CONSULTA_PROTOCOLO));
	}
	
	@Test
	public void svcRs(){
		Local local = Local.SVC_RS;
		Versao versao = Versao.V3_10;
		assertEquals("https://nfe.sefazvirtual.rs.gov.br/ws/NfeAutorizacao/NFeAutorizacao.asmx",producao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://nfe.sefazvirtual.rs.gov.br/ws/NfeRetAutorizacao/NFeRetAutorizacao.asmx",producao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://nfe.sefazvirtual.rs.gov.br/ws/recepcaoevento/recepcaoevento.asmx",producao(local,Versao.V1_00,Servico.EVENTO));
		assertEquals("https://nfe.sefazvirtual.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx",producao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://nfe.sefazvirtual.rs.gov.br/ws/NfeConsulta/NfeConsulta2.asmx",producao(local,versao,Servico.CONSULTA_PROTOCOLO));
		//Homologação
		assertEquals("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeAutorizacao/NFeAutorizacao.asmx",homologacao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeRetAutorizacao/NFeRetAutorizacao.asmx",homologacao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/recepcaoevento/recepcaoevento.asmx",homologacao(local,Versao.V1_00,Servico.EVENTO));
		assertEquals("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeStatusServico/NfeStatusServico2.asmx",homologacao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://homologacao.nfe.sefazvirtual.rs.gov.br/ws/NfeConsulta/NfeConsulta2.asmx",homologacao(local,versao,Servico.CONSULTA_PROTOCOLO));
	}
	
	@Test
	public void ba(){
		Local local = Local.BA;
		Versao versao = Versao.V3_10;
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/NfeAutorizacao/NfeAutorizacao.asmx",producao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/NfeRetAutorizacao/NfeRetAutorizacao.asmx",producao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/sre/recepcaoevento.asmx",producao(local,Versao.V2_00,Servico.EVENTO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/nfenw/CadConsultaCadastro2.asmx",producao(local,versao,Servico.CONSULTA_CADASTRO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/NfeStatusServico/NfeStatusServico.asmx",producao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/NfeConsulta/NfeConsulta.asmx",producao(local,versao,Servico.CONSULTA_PROTOCOLO));
		assertEquals("https://nfe.sefaz.ba.gov.br/webservices/NfeInutilizacao/NfeInutilizacao.asmx",producao(local,versao,Servico.INUTILIZACAO));
		//Homologação
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/NfeAutorizacao/NfeAutorizacao.asmx",homologacao(local,versao,Servico.AUTORIZACAO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/NfeRetAutorizacao/NfeRetAutorizacao.asmx",homologacao(local,versao,Servico.RET_AUTORIZACAO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/sre/recepcaoevento.asmx",homologacao(local,Versao.V2_00,Servico.EVENTO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/nfenw/CadConsultaCadastro2.asmx",homologacao(local,versao,Servico.CONSULTA_CADASTRO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/NfeStatusServico/NfeStatusServico.asmx",homologacao(local,versao,Servico.STATUS_SERVICO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/NfeConsulta/NfeConsulta.asmx",homologacao(local,versao,Servico.CONSULTA_PROTOCOLO));
		assertEquals("https://hnfe.sefaz.ba.gov.br/webservices/NfeInutilizacao/NfeInutilizacao.asmx",homologacao(local,versao,Servico.INUTILIZACAO));
		
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
