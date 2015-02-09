package com.hadrion.nfe.dominio.modelo.lote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;

@Service
public class EnviarLoteService {
	
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	public void enviar(Lote lote) {
		Certificado certificado = empresaRepositorio.obterCertificadoPelaEmpresa(lote.empresaId());
		RetornoAutorizacao retorno=null; 
		
		try {
			retorno = autorizacaoService.autorizar(lote,certificado);
		} catch (Throwable t) {
			t.printStackTrace();
			lote.erroTransmissao(
					new Mensagem(-1,StringUtils.substring(t.getMessage(),0,3999)));
			return;
		}
		
		if (retorno != null && retorno.sucesso())
			lote.transmitido(retorno.recibo().numero());
		else 
			lote.inconsistente(retorno.erro());
	}
}
