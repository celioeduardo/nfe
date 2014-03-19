package com.hadrion.nfe.dominio.modelo.lote;

import java.util.Set;

import com.hadrion.nfe.dominio.modelo.nf.NotaFiscal;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalId;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRepositorio;

public class LoteService {
	
	private NotaFiscalRepositorio notaFiscalRepositorio;
	
	public LoteService(NotaFiscalRepositorio notaFiscalRepositorio){
		this.notaFiscalRepositorio=notaFiscalRepositorio;
	}
	
	public Lote gerarLote(Set<NotaFiscalId> notas){
		
		for (NotaFiscalId notaFiscalId : notas) {
			NotaFiscal nf = notaFiscalRepositorio.notaFiscalPeloId(notaFiscalId);
			
			if (nf == null)
				throw new IllegalArgumentException(
						"Nota Fiscal "+notaFiscalId.id()+
						" não encontrada.");
			
			if (!nf.pendenteDeTransmissao())
				throw new IllegalArgumentException(
						"Nota Fiscal "+notaFiscalId.id()+
						" não está Pendente de Transmissão.");
		}
		
		return new Lote(notas);
	}
	
}
