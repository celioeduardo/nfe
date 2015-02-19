package com.hadrion.nfe.dominio.modelo.lote;

import java.util.HashSet;
import java.util.Set;

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
		return Lote.gerarEmProducao(notas,empresaDasNotas(notas));
	}
	
	private EmpresaId empresaDasNotas(Set<NotaFiscal> notas){
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
			
			assertNotaNaoEstaPendenteEmOutrosLotes(nf, ambiente);
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
