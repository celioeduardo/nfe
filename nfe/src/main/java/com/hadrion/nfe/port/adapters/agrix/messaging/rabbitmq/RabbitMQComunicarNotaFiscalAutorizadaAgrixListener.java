package com.hadrion.nfe.port.adapters.agrix.messaging.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hadrion.comum.notificacao.NotificationReader;
import com.hadrion.nfe.dominio.modelo.lote.NotaFiscalAutorizada;
import com.hadrion.nfe.port.adapters.agrix.repositorio.AgrixService;
import com.hadrion.nfe.port.adapters.messaging.rabbitmq.RabbitNfeEventoListener;

@Component
//@Profile({"dev", "prod"})
@Profile({"!test"})
public class RabbitMQComunicarNotaFiscalAutorizadaAgrixListener extends RabbitNfeEventoListener{

	@Autowired
	protected AgrixService agrixService;
	
	@Override
	public String[] ouvindoPara() {
		return new String[]{NotaFiscalAutorizada.class.getName()};	
	}

	@Override
	public void tratar(String tipo, String mensagemTexto) {
		
		System.out.printf("[%s]: tipo: %s, mensagem: %s\n",getClass().getSimpleName(),tipo,mensagemTexto);
		
		NotificationReader reader = new NotificationReader(mensagemTexto);
		
		String notaFiscalId = reader.eventStringValue("notaFiscalId.id");
		String chaveAcesso = reader.eventStringValue("chaveAcesso.chave");
		String ambiente = reader.eventStringValue("ambiente");
		String filialId = reader.eventStringValue("filialId.id");
		
		agrixService.comunicarNotaAutorizada(notaFiscalId,chaveAcesso,ambiente,filialId);
		
	}

}
