package com.hadrion.nfe.dominio.modelo.recepcao;

import com.hadrion.nfe.dominio.modelo.lote.Lote;
import com.hadrion.nfe.dominio.modelo.lote.NumeroReciboLote;
import com.hadrion.nfe.dominio.modelo.portal.Mensagem;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.RecepcaoLoteService;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.ReciboLote;
import com.hadrion.nfe.dominio.modelo.portal.recepcao.RetornoRecepcaoLote;

public class MockRecepcaoLoteService implements RecepcaoLoteService{
	
	private Mensagem erro;
	private NumeroReciboLote numeroReciboLote;
	
	
	public MockRecepcaoLoteService(
			Mensagem erro){
		this.erro = erro;
	}
	
	public MockRecepcaoLoteService(
			String numeroReciboLote){
		this.numeroReciboLote = new NumeroReciboLote(numeroReciboLote);
	}
	
	@Override
	public RetornoRecepcaoLote recepcionar(Lote lote) {
		return numeroReciboLote != null ?
				new RetornoRecepcaoLote(
						new ReciboLote(numeroReciboLote)) :
				new RetornoRecepcaoLote(erro);
	}

}
