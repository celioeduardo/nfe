package com.hadrion.nfe.port.adapters.portal.ws;

import java.util.HashMap;
import java.util.Map;

import com.hadrion.nfe.dominio.modelo.Ambiente;


public class EndPoints {
	
	private static EndPoints instancia;
	private Map<EndPoint, String> mapa;
	
	public EndPoints(){
		configurarMg();
		configurarSp();
		configurarBa();
	}
	
	private void configurarMg(){
		Local uf = Local.MG;
		Versao versao = Versao.V3_10;
		put(Ambiente.PRODUCAO,uf,versao,Servico.AUTORIZACAO,"https://nfe.fazenda.mg.gov.br/nfe2/services/NfeAutorizacao");
		put(Ambiente.PRODUCAO,uf,versao,Servico.RET_AUTORIZACAO,"https://nfe.fazenda.mg.gov.br/nfe2/services/NfeRetAutorizacao");
		put(Ambiente.PRODUCAO,uf,Versao.V1_00,Servico.EVENTO,"https://nfe.fazenda.mg.gov.br/nfe2/services/RecepcaoEvento");
		put(Ambiente.PRODUCAO,uf,Versao.V2_00,Servico.CONSULTA_CADASTRO,"https://nfe.fazenda.mg.gov.br/nfe2/services/cadconsultacadastro2");
		put(Ambiente.PRODUCAO,uf,versao,Servico.STATUS_SERVICO,"https://nfe.fazenda.mg.gov.br/nfe2/services/NfeStatus2");
		put(Ambiente.PRODUCAO,uf,versao,Servico.CONSULTA_PROTOCOLO,"https://nfe.fazenda.mg.gov.br/nfe2/services/NfeConsulta2");
		put(Ambiente.PRODUCAO,uf,versao,Servico.INUTILIZACAO,"https://nfe.fazenda.mg.gov.br/nfe2/services/NfeInutilizacao2");

		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.AUTORIZACAO,"https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeAutorizacao");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.RET_AUTORIZACAO,"https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeRetAutorizacao");
		put(Ambiente.HOMOLOGACAO,uf,Versao.V1_00,Servico.EVENTO,"https://hnfe.fazenda.mg.gov.br/nfe2/services/RecepcaoEvento");
		put(Ambiente.HOMOLOGACAO,uf,Versao.V2_00,Servico.CONSULTA_CADASTRO,"https://nfe.fazenda.mg.gov.br/nfe2/services/cadconsultacadastro2");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.STATUS_SERVICO,"https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeStatus2");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.CONSULTA_PROTOCOLO,"https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeConsulta2");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.INUTILIZACAO,"https://hnfe.fazenda.mg.gov.br/nfe2/services/NfeInutilizacao2");
	}
	
	private void configurarSp(){
		Local uf = Local.SP;
		Versao versao = Versao.V3_10;
		put(Ambiente.PRODUCAO,uf,versao,Servico.AUTORIZACAO,"https://nfe.fazenda.sp.gov.br/ws/nfeautorizacao.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.RET_AUTORIZACAO,"https://nfe.fazenda.sp.gov.br/ws/nferetautorizacao.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.EVENTO,"https://nfe.fazenda.sp.gov.br/ws/recepcaoevento.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.CONSULTA_CADASTRO,"https://nfe.fazenda.sp.gov.br/ws/cadconsultacadastro2.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.STATUS_SERVICO,"https://nfe.fazenda.sp.gov.br/ws/nfestatusservico2.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.CONSULTA_PROTOCOLO,"https://nfe.fazenda.sp.gov.br/ws/nfeconsulta2.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.INUTILIZACAO,"https://nfe.fazenda.sp.gov.br/ws/nfeinutilizacao2.asmx");
		
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.AUTORIZACAO,"https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeautorizacao.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.RET_AUTORIZACAO,"https://homologacao.nfe.fazenda.sp.gov.br/ws/nferetautorizacao.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.EVENTO,"https://homologacao.nfe.fazenda.sp.gov.br/ws/recepcaoevento.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.CONSULTA_CADASTRO,"https://homologacao.nfe.fazenda.sp.gov.br/ws/cadconsultacadastro2.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.STATUS_SERVICO,"https://homologacao.nfe.fazenda.sp.gov.br/ws/nfestatusservico2.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.CONSULTA_PROTOCOLO,"https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeconsulta2.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.INUTILIZACAO,"https://homologacao.nfe.fazenda.sp.gov.br/ws/nfeinutilizacao2.asmx");
		
	}
	private void configurarBa(){
		//TODO endpoint bahia
		Local uf = Local.BA;
		Versao versao = Versao.V3_10;
		put(Ambiente.PRODUCAO,uf,versao,Servico.AUTORIZACAO,"https://nfe.sefaz.ba.gov.br/webservices/NfeAutorizacao/NfeAutorizacao.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.RET_AUTORIZACAO,"https://nfe.sefaz.ba.gov.br/webservices/NfeRetAutorizacao/NfeRetAutorizacao.asmx");	
		put(Ambiente.PRODUCAO,uf,versao,Servico.EVENTO,"https://nfe.sefaz.ba.gov.br/webservices/sre/recepcaoevento.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.CONSULTA_CADASTRO,"https://nfe.sefaz.ba.gov.br/webservices/nfenw/CadConsultaCadastro2.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.STATUS_SERVICO,"https://nfe.sefaz.ba.gov.br/webservices/NfeStatusServico/NfeStatusServico.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.CONSULTA_PROTOCOLO,"https://nfe.sefaz.ba.gov.br/webservices/NfeConsulta/NfeConsulta.asmx");
		put(Ambiente.PRODUCAO,uf,versao,Servico.INUTILIZACAO,"https://nfe.sefaz.ba.gov.br/webservices/NfeInutilizacao/NfeInutilizacao.asmx");
		//homologacao
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.AUTORIZACAO,"https://hnfe.sefaz.ba.gov.br/webservices/NfeAutorizacao/NfeAutorizacao.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.RET_AUTORIZACAO,"https://hnfe.sefaz.ba.gov.br/webservices/NfeRetAutorizacao/NfeRetAutorizacao.asmx");		
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.EVENTO,"https://hnfe.sefaz.ba.gov.br/webservices/sre/recepcaoevento.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.CONSULTA_CADASTRO,"https://hnfe.sefaz.ba.gov.br/webservices/nfenw/CadConsultaCadastro2.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.STATUS_SERVICO,"https://hnfe.sefaz.ba.gov.br/webservices/NfeStatusServico/NfeStatusServico.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.CONSULTA_PROTOCOLO,"https://hnfe.sefaz.ba.gov.br/webservices/NfeConsulta/NfeConsulta.asmx");
		put(Ambiente.HOMOLOGACAO,uf,versao,Servico.INUTILIZACAO,"https://hnfe.sefaz.ba.gov.br/webservices/NfeInutilizacao/NfeInutilizacao.asmx");
	}
	
	private void put(Ambiente ambiente, Local local, Versao versao, Servico servico, String uri){
		getMapa().put(new EndPoint(ambiente, local, versao, servico),uri);
	}
	
	Map<EndPoint, String> getMapa(){
		if (mapa == null)
			mapa = new HashMap<EndPoint, String>();
		return mapa;
	}
	
	static EndPoints getInstancia(){
		if (instancia == null)
			instancia = new EndPoints();
		return instancia;
	}
	
	public static String homologacao(Local local, Versao versao, Servico servico) {
		return getInstancia().mapa.get(new EndPoint(Ambiente.HOMOLOGACAO, local, versao, servico));
	}
	public static String producao(Local local, Versao versao, Servico servico) {
		return getInstancia().mapa.get(new EndPoint(Ambiente.PRODUCAO, local, versao, servico));
	}
	
	public static String obter(Ambiente ambiente,Local local, Versao versao, Servico servico) {
		return getInstancia().mapa.get(new EndPoint(ambiente, local, versao, servico));
	}
	
}
