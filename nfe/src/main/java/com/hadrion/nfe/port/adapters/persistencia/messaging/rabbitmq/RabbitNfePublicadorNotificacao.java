package com.hadrion.nfe.port.adapters.persistencia.messaging.rabbitmq;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hadrion.comum.port.adapter.messaging.rabbitmq.RabbitMQPublicadorNotificacao;

@Component
@Profile({"dev","prod"})
public class RabbitNfePublicadorNotificacao extends
		RabbitMQPublicadorNotificacao {

	@Override
	public String exchangeName() {
		return "hadrionNfe";
	}

}
