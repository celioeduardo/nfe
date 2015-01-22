package com.hadrion.nfe.port.adapters.persistencia.messaging.rabbitmq;

import java.io.IOException;

import javax.mail.MessagingException;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hadrion.comum.notificacao.NotificationReader;
import com.hadrion.nfe.aplicacao.nf.EnviarEmailComando;
import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.dominio.modelo.lote.NotaFiscalAutorizada;

@Component
//@Profile({"dev", "prod"})
@Profile({"!test"})
public class RabbitMQEnvioEmailNotaFiscalAutorizadaListener extends RabbitNfeEventoListener{

	@Autowired
	protected NotaFiscalAplicacaoService notaFiscalAplicacaoService;
	
	@Override
	public String[] ouvindoPara() {
		return new String[]{NotaFiscalAutorizada.class.getName()};	
	}

	@Override
	public void tratar(String tipo, String mensagemTexto) {
		
		System.out.printf("[%s]: tipo: %s, mensagem: %s\n",getClass().getSimpleName(),tipo,mensagemTexto);
		
		NotificationReader reader = new NotificationReader(mensagemTexto);
		
		String notaFiscalId = reader.eventStringValue("notaFiscalId.id");
		
		EnviarEmailComando comando = new EnviarEmailComando(notaFiscalId);
		
		try {
			notaFiscalAplicacaoService.enviarEmail(comando);
		} catch (IOException | MessagingException | JRException e) {
			e.printStackTrace();
		}
	}

}
