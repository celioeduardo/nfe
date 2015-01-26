package com.hadrion.nfe.port.adapters.messaging.rabbitmq;

import com.hadrion.comum.port.adapter.messaging.rabbitmq.RabbitMQExchangeListener;

public abstract class RabbitNfeEventoListener extends RabbitMQExchangeListener{

	@Override
	public String exchangeName() {
		return "hadrionNfe";
	}

}
