package com.hadrion.nfe.port.adapters.portal.ws;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.ModoOperacao;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.nf.TipoEmissao;
/** 
UF que utilizam a SVAN - Sefaz Virtual do Ambiente Nacional: MA, PA, PI 
UF que utilizam a SVRS - Sefaz Virtual do RS: 
  - Para serviço de Consulta Cadastro: AC, RN, PB, SC 
  - Para demais serviços relacionados com o sistema da NF-e: AC, AL, AP, DF, PB, RJ, RN, RO, RR, SC, SE, TO 
Autorizadores em contingência: 
  - UF que utilizam a SVC-AN - Sefaz Virtual de Contingência Ambiente Nacional: 
      AC, AL, AP, DF, ES, MG, PB, RJ, RN, RO, RR, RS, SC, SE, SP, TO 
  - UF que utilizam a SVC-RS - Sefaz Virtual de Contingência Rio Grande do Sul: 
      AM, BA, CE, GO, MA, MS, MT, PA, PE, PI, PR

Autorizadores: AM BA CE GO MG MA MS MT PE PR RS SP SVAN SVRS SCAN SVC-AN SVC-RS
 */

public enum Local {
	BA(Versao.V3_10,Versao.V2_00,Versao.V3_10),
	MG(Versao.V3_10,Versao.V1_00,Versao.V2_00),
	SP(Versao.V3_10,Versao.V1_00,Versao.V3_10),
	SVC_AN(Versao.V3_10,Versao.V1_00,Versao.V3_10), 
	SVC_RS(Versao.V3_10,Versao.V1_00,Versao.V3_10);

	private Versao versao;
	private Versao versaoEvento;
	private Versao versaoInutilizacao;
	
	Local(Versao versao, Versao versaoEvento, Versao versaoInutilizacao){
		this.versao = versao;
		this.versaoEvento = versaoEvento;
		this.versaoInutilizacao = versaoInutilizacao;
	}
	
	Local(Versao versao){
		this(versao,versao,versao);
	}
	
	public static Local obter(TipoEmissao tipoEmissao, Uf uf) {
		if (TipoEmissao.SVC_AN == tipoEmissao)
			return SVC_AN;
		if (TipoEmissao.SVC_RS == tipoEmissao)
			return SVC_RS;
		
		if (TipoEmissao.NORMAL == tipoEmissao ||
			TipoEmissao.FS_DA == tipoEmissao ||
			TipoEmissao.FS_IA == tipoEmissao ||
			TipoEmissao.DPEC == tipoEmissao){
			return localPelaUf(uf);
		}
		return null;
	}
	
	private static Local localPelaUf(Uf uf){
		switch (uf) {
		case BA:return Local.BA;
		case MG:return Local.MG;
		case SP:return Local.SP;
		default:
			break;
		}
		return null;
	}

	public static Local obterPeloModoOperacao(ModoOperacao modoOperacao, Uf uf) {
		if (ModoOperacao.SVC  == modoOperacao)
			return contingenciaPelaUf(uf);
		else 
			return localPelaUf(uf);
	}
	
	public static Local contingenciaPelaUf(Uf uf){
		switch (uf) {
		case AC:
		case AL:
		case AP: 
		case DF: 
		case ES:
		case MG: 
		case PB:
		case RJ: 
		case RN: 
		case RO: 
		case RR: 
		case RS: 
		case SC: 
		case SE: 
		case SP: 
		case TO :
			return Local.SVC_AN;
		default:
			return Local.SVC_RS;
		}
	}

	public String endpointAutorizacao(Ambiente ambiente) {
		return EndPoints.obter(ambiente, this, versao, Servico.AUTORIZACAO);
	}

	public String endpointRetornoAutorizacao(Ambiente ambiente) {
		return EndPoints.obter(ambiente, this, versao, Servico.RET_AUTORIZACAO);
	}

	public String endpointEvento(Ambiente ambiente) {
		return EndPoints.obter(ambiente, this, versaoEvento, Servico.EVENTO);
	}

	public String endpointInutilizacao(Ambiente ambiente) {
		return EndPoints.obter(ambiente, this, versaoInutilizacao, Servico.INUTILIZACAO);
	}
}
