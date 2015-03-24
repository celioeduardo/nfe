package com.hadrion.nfe.dominio.modelo.nf;

import java.util.Date;
import java.util.List;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.notista.NotistaId;
import com.hadrion.nfe.dominio.modelo.portal.ChaveAcesso;


public interface NotaFiscalRepositorio {

	public List<DescritorNotaFiscal> notasPendentesAutorizacaoResumo(
			Ambiente ambiente, Double empresa,FilialId filial,Date inicio,Date fim,String usuario,NotaFiscalId notaFiscalId);
	public List<NotaFiscal> notasPendentesAutorizacao(List<NotaFiscalId> notas, Ambiente ambiente);
	public List<NotaFiscal> notasPendentesAutorizacao(FilialId filialId,Ambiente ambiente);
	
	public NotaFiscal notaFiscalPeloId(NotaFiscalId notaFiscalId);
	
	public void limpar();
	public void salvar(NotaFiscal notaFiscal);
	public NotaFiscal notaFiscalPelaChave(ChaveAcesso chave, Ambiente ambiente);
	public List<NotaFiscal> notasAutorizadas(FilialId filialId,Ambiente ambiente);
	public List<NotaFiscal> notasAutorizadas(FilialId filialId,Ambiente ambiente,Long numero);
	public List<NotaFiscal> notasAutorizadas(FilialId filialId, Ambiente ambiente,NotistaId notistaId);
	public List<NotaFiscal> notasCanceladas(FilialId filialId, Ambiente ambiente);
	public List<NotaFiscal> notasCanceladas(FilialId filialId, Ambiente ambiente,NotistaId notistaId);
	public List<NotaFiscal> notasAutorizadasNaoImpressas(FilialId filialId,Ambiente ambiente);
	public List<NotaFiscal> notasAutorizadasNaoImpressas(FilialId filialId,Ambiente ambiente,Long numero);
	public List<NotaFiscal> notasAutorizadasNaoImpressas(FilialId filialId,Ambiente ambiente,NotistaId notistaId);
	public NotaFiscal notaPendenteAutorizacao(NotaFiscalId notaFiscalId);
}
