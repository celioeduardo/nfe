package com.hadrion.nfe.dominio.modelo.portal.inutilizacao;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.ibge.Uf;
import com.hadrion.nfe.dominio.modelo.inutilizacao.Inutilizacao;
import com.hadrion.nfe.port.adapters.portal.ws.Local;
import com.hadrion.nfe.tipos.Cnpj;


public interface InutilizacaoPortalService {

	RetornoInutilizacao inutilizar(Inutilizacao inutilizacao,Certificado certificado,
			Local local,Uf uf, Cnpj cnpj);
}
