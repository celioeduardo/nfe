package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hadrion.nfe.dominio.modelo.Ambiente;
import com.hadrion.nfe.dominio.modelo.empresa.EmpresaId;
import com.hadrion.nfe.dominio.modelo.filial.Filial;
import com.hadrion.nfe.dominio.modelo.filial.FilialId;
import com.hadrion.nfe.dominio.modelo.filial.FilialRepositorio;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

@Service
public class GeracaoLoteService {
	
	@Autowired
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	@Autowired
	private FilialRepositorio filialRepositorio;
	
	@Autowired
	private LoteRepositorio loteRepositorio;
	
	private static final Logger logger = LoggerFactory.getLogger(GeracaoLoteService.class);
	
	public Lote gerarLoteEmHomologacao(NotaFiscal nota){
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(nota);
		return gerarLoteEmHomologacao(notas);
	}
	public Lote gerarLoteEmHomologacao(Set<NotaFiscal> notas){
		assertPreCondicoes(notas, Ambiente.HOMOLOGACAO);		
		return Lote.gerarEmHomologacao(notas,empresaDasNotas(notas));
	}
	
	public Lote gerarLoteEmProducao(NotaFiscal nota) {
		Set<NotaFiscal> notas = new HashSet<NotaFiscal>();
		notas.add(nota);
		return gerarLoteEmProducao(notas);
	}
	
	public Lote gerarLoteEmProducao(Set<NotaFiscal> notas) {
		assertPreCondicoes(notas, Ambiente.PRODUCAO);
		
		logger.debug("gerando lote em produção de {} nota(s)...",notas.size()); 
		
		Lote lote = Lote.gerarEmProducao(notas,empresaDasNotas(notas));
		
		logger.debug("lote {} gerado",lote.numero());
		
		return lote;
	}
	
	private EmpresaId empresaDasNotas(Set<NotaFiscal> notas){
		
		logger.debug("definindo empresa das notas...");
		
		EmpresaId empresaId = null;
		
		Set<FilialId> filiais = new HashSet<FilialId>();
		
		for (NotaFiscal nf: notas) {
			if (empresaId == null){
				empresaId = filial(nf.filialId()).empresaId();
				filiais.add(nf.filialId());
			} else if (!filiais.contains(nf.filialId())){
				if (!empresaId.equals(filial(nf.filialId()).empresaId()))
					throw new RuntimeException("Todas as Notas Fiscais devem ser da mesma Empresa.");
				else
					filiais.add(nf.filialId());
			}
		}
		
		logger.debug("empresa das notas definida");
		
		return empresaId;
	}
	
	private Filial filial(FilialId filialId){
		return filialRepositorio.obterFilial(filialId);
	}

	private void assertPreCondicoes(Set<NotaFiscal> notas, Ambiente ambiente){
		for (NotaFiscal nf : notas){ 
			if (nf.ambiente() != ambiente)
				throw new IllegalArgumentException(
						"Nota Fiscal "+nf.numero()+
						" gerada em ambiente diferente. ["+nf.ambiente()+"]");
			
			logger.debug("veficando se Nota está pendente em outros lotes...");
			assertNotaNaoEstaPendenteEmOutrosLotes(nf, ambiente);
			logger.debug("veficação completada");
		}
	}
	
	private void assertNotaNaoEstaPendenteEmOutrosLotes(NotaFiscal nf, 
			Ambiente ambiente){
		assertLotesNaoEstaoPendentes(
				loteRepositorio.lotesDaNota(nf.notaFiscalId()),nf,ambiente);
	}
	
	private void assertLotesNaoEstaoPendentes(
			Set<Lote> lotes, NotaFiscal nf, Ambiente ambiente){
		for (Lote lote :lotes) 
			if (lote.ambiente() == ambiente && 
				(lote.estaNaoEnviado() || lote.estaProcessando()))
				throw new IllegalArgumentException(
						"Nota Fiscal "+nf.numero()+
						" já está no Lote "+lote.numero());
	}	
}
