package com.hadrion.nfe.port.adapters.messaging.rabbitmq;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hadrion.comum.notificacao.NotificationReader;
import com.hadrion.nfe.aplicacao.nf.DefinirNotaComoAutorizadaComando;
import com.hadrion.nfe.aplicacao.nf.DefinirNotaComoRejeitadaComando;
import com.hadrion.nfe.aplicacao.nf.DefinirNotaFiscalEmLoteComando;
import com.hadrion.nfe.aplicacao.nf.NotaFiscalAplicacaoService;
import com.hadrion.nfe.dominio.modelo.lote.AutorizacaoNotaFiscalRecebida;
import com.hadrion.nfe.dominio.modelo.lote.NotaFiscalAdicionadaNoLote;
import com.hadrion.nfe.dominio.modelo.nf.NotaFiscalRejeitada;

@Component
//@Profile({"dev", "prod"})
@Profile({"!test"})
public class RabbitMQNotaFiscalListener extends RabbitNfeEventoListener{

	@Autowired
	protected NotaFiscalAplicacaoService notaFiscalAplicacaoService;
	
	@Override
	public String[] ouvindoPara() {
		return new String[]{
				NotaFiscalAdicionadaNoLote.class.getName(), 
				AutorizacaoNotaFiscalRecebida.class.getName(), 
				NotaFiscalRejeitada.class.getName()
		};		
	}

	@Override
	public void tratar(String tipo, String mensagemTexto) {
		
		if (tipo.equals(NotaFiscalRejeitada.class.getName()))
			tratarNotaFiscalRejeitada(tipo,mensagemTexto);
		else if (tipo.equals(AutorizacaoNotaFiscalRecebida.class.getName()))
			tratarAutorizacaoNotaFiscalRecebida(tipo,mensagemTexto);
		else if (tipo.equals(NotaFiscalAdicionadaNoLote.class.getName()))
			tratarNotaFiscalAdicionadaNoLote(tipo,mensagemTexto);
	}
	
	private void tratarNotaFiscalRejeitada(String tipo,String mensagemTexto){
		
		System.out.printf("[%s]: tipo: %s, mensagem: %s\n",getClass().getSimpleName(),tipo,mensagemTexto);
		
		NotificationReader reader = new NotificationReader(mensagemTexto);
		
		String notaFiscalId = reader.eventStringValue("notaFiscalId.id");
		int msgCodigo = reader.eventIntegerValue("mensagem.codigo");
		String msgDescricao = reader.eventStringValue("mensagem.descricao");
		
		DefinirNotaComoRejeitadaComando comando = 
				new DefinirNotaComoRejeitadaComando(notaFiscalId, msgCodigo, msgDescricao); 
		
		notaFiscalAplicacaoService.definirNotaComoRejeitada(comando);
	}
	
	public void tratarAutorizacaoNotaFiscalRecebida(String tipo,String mensagemTexto){
		
		System.out.printf("[%s]: tipo: %s, mensagem: %s\n",getClass().getSimpleName(),tipo,mensagemTexto);
		
		NotificationReader reader = new NotificationReader(mensagemTexto);
		
		String notaFiscalId = reader.eventStringValue("notaFiscalId.id");
		int msgCodigo = reader.eventIntegerValue("mensagem.codigo");
		String msgDescricao = reader.eventStringValue("mensagem.descricao");
		String numeroProtocolo = reader.eventStringValue("numeroProtocolo");
		String xmlProtocolo = reader.eventStringValue("xmlProtocolo");
		//Date dataHoraAutorizacao = DataUtil.parseComTimezone(reader.eventStringValue("dataHoraAutorizacao"));
		Date dataHoraAutorizacao = reader.eventDateValue("dataHoraAutorizacao");
		DefinirNotaComoAutorizadaComando comando = new DefinirNotaComoAutorizadaComando(
				notaFiscalId, numeroProtocolo, msgCodigo, msgDescricao, dataHoraAutorizacao, xmlProtocolo);
		
		notaFiscalAplicacaoService.definirNotaComoAutorizada(comando);
		
	}
	
	public void tratarNotaFiscalAdicionadaNoLote(String tipo,String mensagemTexto){
		
		System.out.printf("[%s]: tipo: %s, mensagem: %s\n",getClass().getSimpleName(),tipo,mensagemTexto);
		
		NotificationReader reader = new NotificationReader(mensagemTexto);
		
		String notaFiscalId = reader.eventStringValue("notaFiscalId");
		
		DefinirNotaFiscalEmLoteComando comando = 
				new DefinirNotaFiscalEmLoteComando(notaFiscalId); 
		
		try {
			notaFiscalAplicacaoService.definirNotaEmLote(comando);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
