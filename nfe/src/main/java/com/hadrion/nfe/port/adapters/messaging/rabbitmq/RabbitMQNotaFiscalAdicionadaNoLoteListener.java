package com.hadrion.nfe.port.adapters.messaging.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hadrion.comum.notificacao.NotificationReader;
import com.hadrion.nfe.aplicacao.nf.DefinirNotaFiscalEmLoteComando;
import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.dominio.modelo.lote.NotaFiscalAdicionadaNoLote;

@Component
//@Profile({"dev", "prod"})
@Profile({"!test"})
public class RabbitMQNotaFiscalAdicionadaNoLoteListener extends RabbitNfeEventoListener{

	@Autowired
	protected NotaFiscalAplicacaoService notaFiscalAplicacaoService;
	
	@Override
	public String[] ouvindoPara() {
		return new String[]{NotaFiscalAdicionadaNoLote.class.getName()};	
	}

	@Override
	public void tratar(String tipo, String mensagemTexto) {
		
		System.out.printf("[%s]: tipo: %s, mensagem: %s\n",getClass().getSimpleName(),tipo,mensagemTexto);
		
		NotificationReader reader = new NotificationReader(mensagemTexto);
		
		String notaFiscalId = reader.eventStringValue("notaFiscalId");
		
		DefinirNotaFiscalEmLoteComando comando = 
				new DefinirNotaFiscalEmLoteComando(notaFiscalId); 
		
		notaFiscalAplicacaoService.definirNotaEmLote(comando);
		
	}

}
