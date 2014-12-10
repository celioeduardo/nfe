package com.hadrion.comum.port.adapter.messaging.rabbitmq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.comum.evento.EventStore;
import com.hadrion.comum.evento.StoredEvent;
import com.hadrion.comum.notificacao.Notificacao;
import com.hadrion.comum.notificacao.NotificacaoSerializer;
import com.hadrion.comum.notificacao.PublicadorNotificacao;
import com.hadrion.comum.notificacao.RastreadorNotificacaoPublicada;
import com.hadrion.comum.notificacao.RastreadorNotificacaoPublicadaStore;

public abstract class RabbitMQPublicadorNotificacao implements PublicadorNotificacao {

	@Autowired
	private EventStore eventStore;
	
	@Autowired
	private RastreadorNotificacaoPublicadaStore rastreadorNotificacaoPublicadaStore;
	
	@Autowired
	private RabbitTemplate amqpTemplate;
	
	protected RabbitMQPublicadorNotificacao(){}
	
	@Override
	public void publicarNotificacoes() {
		RastreadorNotificacaoPublicada rastreador = rastreadorStore().rastreadorNotificacaoPublicada();
		
		List<Notificacao> notificacoes = listaNotificacoesNaoPublicadas(
				rastreador.notificacaoIdMaisRecentePublicada());
		
		try {
            for (Notificacao notificacao : notificacoes) 
                this.publicar(notificacao);

            this.rastreadorStore()
                .rastrearNotificacaoMaisRecentePublicada(
                    rastreador,
                    notificacoes);
        } finally {
            
        }
	}

	private void publicar(Notificacao notificacao) {
		String msg = NotificacaoSerializer
				.instance()
				.serialize(notificacao);
		
		Message message = MessageBuilder
				.withBody(msg.getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
				.setMessageId(Long.toString(notificacao.notificacaoId()))
				.setTimestamp(notificacao.ocorridoEm())
				.setType(notificacao.nomeTipo())
				.build();
		
		this.amqpTemplate.send(exchangeName(),message);
	}

	@Override
	public boolean somenteConfirmacaoTesteInterno() {
		throw new UnsupportedOperationException();
	}
	
	private List<Notificacao> listaNotificacoesNaoPublicadas(long idMensagemPublicadaMaisRecente){
		List<StoredEvent> eventosArmazenados = eventStore().todosEventosArmazenadosDesde(idMensagemPublicadaMaisRecente); 
		return notificacoesPelos(eventosArmazenados);
	}
	
	private List<Notificacao> notificacoesPelos(List<StoredEvent> eventosArmazenados){
		List<Notificacao> result = new ArrayList<Notificacao>(eventosArmazenados.size());
		for (StoredEvent eventoArmazenado : eventosArmazenados) {
			EventoDominio eventoDominio = eventoArmazenado.paraEventoDominio();
			
			Notificacao notificacao = new Notificacao(eventoArmazenado.eventId(), eventoDominio);
			
			result.add(notificacao);
		}
		return result;
	}
	
	private RastreadorNotificacaoPublicadaStore rastreadorStore(){
		return this.rastreadorNotificacaoPublicadaStore;
	}
	
	private EventStore eventStore(){
		return this.eventStore;
	}
	
	public abstract String exchangeName();
	

}
