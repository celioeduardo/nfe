package com.hadrion.nfe.port.adapters.persistencia.messaging.rabbitmq;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hadrion.comum.port.adapter.messaging.rabbitmq.RabbitMQPublicadorNotificacao;

@Component
@Profile({"!test"})
public class RabbitNfePublicadorNotificacao extends
		RabbitMQPublicadorNotificacao {

	@Override
	public String exchangeName() {
		return "hadrionNfe";
	}

}
