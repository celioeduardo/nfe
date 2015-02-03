package com.hadrion.nfe.dominio.modelo.inutilizacao;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.nf.Serie;

public class InutilizacaoFixture {

	public static Inutilizacao inutilizacao(){
		return new Inutilizacao(
				new InutilizacaoId("123456"),
				Ambiente.HOMOLOGACAO,
				new Serie(2),
				100,101,
				"Justificativa de inutilização",
				new FilialId("53-86675642000106"));
	}
	
}
