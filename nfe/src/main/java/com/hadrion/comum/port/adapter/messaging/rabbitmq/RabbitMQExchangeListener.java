package com.hadrion.comum.port.adapter.messaging.rabbitmq;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;

import com.rabbitmq.client.Channel;

public abstract class RabbitMQExchangeListener implements ChannelAwareMessageListener{
	
	 Logger log = LoggerFactory.getLogger(RabbitMQExchangeListener.class);
	 
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	@Autowired
	private Exchange exchange;
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	private SimpleMessageListenerContainer listenerContainer;
	
	private Queue queue;
	
	@PostConstruct
	public void init(){
		queue = new Queue(individualQueueName());
		this.rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to((FanoutExchange) exchange));
        
        listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.setQueues(queue);
        listenerContainer.setMessageListener(this);
        listenerContainer.start();
        
//		System.out.println("["+this.getClass().getSimpleName() + "] \n RabbitTemplate: "+amqpTemplate
//				+"\n RabbitAdmin: "+rabbitAdmin
//				+"\n Exchange: "+exchange
//				+"\n Queue: "+queue
//				+"\n ListenerContainer: "+listenerContainer
//				);
	}
	
	@PreDestroy
	public void cleanUp() throws Exception {
		log.info("Parando Listener container: "+getClass().getName());
		listenerContainer.stop();
		listenerContainer.shutdown();
	}
 	
	@Override
	public void onMessage(Message message, Channel channel) throws Exception {
		String tipo = message.getMessageProperties().getType();
		String texto = new String(message.getBody()); 
		
		if (estaOuvindPara(tipo))
			this.tratar(tipo, texto);
	}
	private String individualQueueName(){
		return exchangeName() + "." + queueName();
	}
	
	private String queueName(){
		return this.getClass().getSimpleName();
	}
	public abstract String exchangeName();
	
	public abstract String[] ouvindoPara();
	
	public abstract void tratar(String tipo, String mensagemTexto);
	
	private boolean estaOuvindPara(String tipo){
		return ArrayUtils.contains(ouvindoPara(), tipo); 
	}
	
}
