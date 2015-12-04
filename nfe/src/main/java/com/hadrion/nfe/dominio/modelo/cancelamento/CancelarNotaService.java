package com.hadrion.nfe.dominio.modelo.cancelamento;

import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.nfe.dominio.modelo.empresa.Empresa;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaRepositorio;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;
import com.hadrion.nfe.dominio.modelo.portal.evento.EventoService;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoEvento;
import com.hadrion.nfe.dominio.modelo.portal.evento.RetornoLoteEvento;
import com.hadrion.nfe.port.adapters.portal.evento.EventoCancelamento;
import com.hadrion.nfe.port.adapters.portal.evento.LoteEvento;
import com.hadrion.nfe.port.adapters.portal.ws.Local;
import com.hadrion.util.DataUtil;

public class CancelarNotaService{
	
	@Autowired
	private EventoService eventoService;
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	@Autowired
	private EmpresaRepositorio empresaRepositorio;
	
	@Autowired
	private FilialRepositorio filialRepositorio;
	
	void configurarCancelamentoNfeService(EventoService cancelamentoNfeService){
		this.eventoService = cancelamentoNfeService;
	}

	public void cancelar(SolicitacaoCancelamento solicitacao) {
		
		NotaFiscal nf = notaFiscalRepositorio.notaFiscalPeloId(solicitacao.notaFiscalId());
		Filial filial = filialRepositorio.obterFilial(nf.filialId());
		Empresa empresa = empresaRepositorio.obterEmpresa(filial.empresaId());
		
		if (!nf.estaAutorizada())
			throw new IllegalArgumentException(
					"Somente Nota Fiscal AUTORIZADA pode ser Cancelada.");
		
		permiteCancelarNotaFical(nf);
		
		EventoCancelamento evento = new EventoCancelamento(
				filial.uf(), 
				nf.ambiente(), 
				filial.cnpj(), 
				nf.chaveAcesso(), 
				DataUtil.agora(), 
				nf.numeroProtocoloAutorizacao(), 
				solicitacao.justificativa());
		
		LoteEvento lote = new LoteEvento(
				1L,  
				evento);
		
		RetornoLoteEvento retorno = this.eventoService
				.enviar(lote,empresa.certificado(),nf.ambiente(),
						Local.obterPeloModoOperacao(filial.modoOperacao(),filial.uf()));
		
		processarRetorno(nf, retorno);
		
		this.notaFiscalRepositorio.salvar(nf);
	}
	
	protected void permiteCancelarNotaFical(NotaFiscal nf){}

	private void processarRetorno(NotaFiscal nf,
			RetornoLoteEvento retornoLote) {
		
		if (!retornoLote.sucesso())
			throw new RuntimeException(retornoLote.mensagem().codigo()+"-"+retornoLote.mensagem().descricao());
		
		RetornoEvento retornoEvento = retornoLote.retornoDaNota(nf.chaveAcesso());
		
		if (retornoEvento == null)
			throw new RuntimeException("Retorno do Evento da Nota Fiscal com chave [" +
					nf.chaveAcesso()+"] não encontrado.");
		
		if (retornoEvento.cancelamentoHomologado())
			nf.cancelar(retornoEvento.numeroProtocolo(),retornoEvento.mensagem(),retornoEvento.dataHoraRegistroEvento());
		else
			throw new RuntimeException(retornoEvento.mensagem().codigo()+"-"+retornoEvento.mensagem().descricao());
	}
	
}
