package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.certificado.Certificado;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.AutorizacaoService;
import com.hadrion.nfe.dominio.modelo.portal.autorizacao.RetornoAutorizacao;

@Service
public class EnviarLoteService {
	
	@Autowired
	private AutorizacaoService autorizacaoService;
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
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
		else {
			Map<NotaFiscalId, String> erros = new HashMap<NotaFiscalId, String>();
			for (Entry<ChaveAcesso, String> e : retorno.erros().entrySet()) {
				NotaFiscal nf = notaPelaChave(e.getKey(), lote.ambiente());
				erros.put(nf.notaFiscalId(), e.getValue());
			}
			lote.inconsistente(retorno.erro(),erros);
		}
	}
	
	private NotaFiscal notaPelaChave(ChaveAcesso chave, Ambiente ambiente){
		return notaFiscalRepositorio.notaFiscalPelaChave(chave, ambiente);
	}
}
