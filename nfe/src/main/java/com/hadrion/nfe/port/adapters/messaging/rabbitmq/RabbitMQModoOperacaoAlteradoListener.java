package com.hadrion.nfe.port.adapters.messaging.rabbitmq;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hadrion.comum.notificacao.NotificationReader;
import com.hadrion.nfe.aplicacao.nf.AtualizarModoOperacaoComando;
import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.dominio.modelo.filial.ModoOperacao;
import com.hadrion.nfe.dominio.modelo.filial.ModoOperacaoAlterado;

@Component
@Profile({"!test"})
public class RabbitMQModoOperacaoAlteradoListener extends RabbitNfeEventoListener{

	@Autowired
	protected NotaFiscalAplicacaoService notaFiscalAplicacaoService;
	
	@Override
	public String[] ouvindoPara() {
		return new String[]{ModoOperacaoAlterado.class.getName()};	
	}

	@Override
	public void tratar(String tipo, String mensagemTexto) {
		
		System.out.printf("[%s]: tipo: %s, mensagem: %s\n",getClass().getSimpleName(),tipo,mensagemTexto);
		
		NotificationReader reader = new NotificationReader(mensagemTexto);
		
		String filialId = reader.eventStringValue("filialId");
		ModoOperacao modoOperacao = ModoOperacao.valueOf(reader.eventStringValue("modoOperacao"));
		Date dataHoraContingencia = reader.eventDateValue("dataHoraContingencia");
		String justificativaContingencia = reader.eventStringValue("justificativaContingencia");
		
		AtualizarModoOperacaoComando comando = new AtualizarModoOperacaoComando(
				filialId, modoOperacao, dataHoraContingencia, justificativaContingencia);
		
		notaFiscalAplicacaoService.atualizarModoOperacao(comando);
		
	}

}
