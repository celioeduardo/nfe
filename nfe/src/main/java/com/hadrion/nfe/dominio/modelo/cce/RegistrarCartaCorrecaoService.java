package com.hadrion.nfe.dominio.modelo.cce;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.empresa.Empresa;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaRepositorio;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.evento.EventoService;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoEvento;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoLoteEvento;
import com.hadrion.nfe.port.adapters.portal.evento.EventoCartaCorrecao;
import com.hadrion.nfe.port.adapters.portal.evento.LoteEvento;
import com.hadrion.nfe.port.adapters.portal.ws.Local;
import com.hadrion.util.DataUtil;

@Service
@Transactional
public class RegistrarCartaCorrecaoService {

	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	private FilialRepositorio filialRepositorio;
	
	public void registar(NotaFiscalId notaFiscalId, String correcao){
		NotaFiscal nf = notaFiscalRepositorio.notaFiscalPeloId(notaFiscalId);
		Filial filial = filialRepositorio.obterFilial(nf.filialId());
		Empresa empresa = empresaRepositorio.obterEmpresa(filial.empresaId());
		
		if (!nf.estaAutorizada())
			throw new IllegalArgumentException(
					"Somente Nota Fiscal AUTORIZADA corrigida com Carta de Correção.");
		
		int sequencia = nf.ultimaSequenciaCartaCorrecao()+1;
				
		EventoCartaCorrecao evento = new EventoCartaCorrecao(
				filial.uf(), 
				nf.ambiente(), 
				filial.cnpj(), 
				nf.chaveAcesso(), 
				DataUtil.agora(), 
				sequencia, 
				correcao);
		
		LoteEvento lote = new LoteEvento(
				1L,  
				evento);
		
		RetornoLoteEvento retorno = this.eventoService
				.enviar(lote,empresa.certificado(),nf.ambiente(),
						Local.obterPeloModoOperacao(filial.modoOperacao(),filial.uf()));
		
		processarRetorno(nf, sequencia, correcao, retorno);
		
		this.notaFiscalRepositorio.salvar(nf);
	}
	
	private void processarRetorno(NotaFiscal nf, int sequencia, String correcao,
			RetornoLoteEvento retornoLote) {
		
		if (!retornoLote.sucesso())
			throw new RuntimeException(retornoLote.mensagem().codigo()+"-"+retornoLote.mensagem().descricao());
		
		RetornoEvento retornoEvento = retornoLote.retornoDaNota(nf.chaveAcesso());
		
		if (retornoEvento == null)
			throw new RuntimeException("Retorno do Evento da Nota Fiscal com chave [" +
					nf.chaveAcesso()+"] não encontrado.");
		
		if (retornoEvento.cartaCorrecaoRegistrada())
			nf.registrarCartaCorrecao(sequencia,correcao,
					retornoEvento.dataHoraRegistroEvento(),
					retornoEvento.xmlEnvio(),
					retornoEvento.xmlRetorno());
		else
			throw new RuntimeException(retornoEvento.mensagem().codigo()+"-"+retornoEvento.mensagem().descricao());
	}
	
}
