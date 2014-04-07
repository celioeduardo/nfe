package com.hadrion.comum.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.comum.dominio.modelo.EventoDominioAssinante;
import com.hadrion.comum.dominio.modelo.EventoDominioPublicador;
import com.hadrion.comum.notificacao.NotificationReader;
import com.hadrion.comum.port.adapter.messaging.Exchanges;
import com.hadrion.comum.port.adapter.messaging.slothmq.SlothClient;
import com.hadrion.comum.port.adapter.messaging.slothmq.SlothServer;

public abstract class RastreadorEventoTest {
	
	private static final boolean RASTREAR_NOTIFICACOES = false;
	
	private TestAppccSlothMQExchangeListener appccSlothMQExchangeListener;
	private TestAppccRabbitMQExchangeListener appccRabbitMQExchangeListener;
	
	private List<Class<? extends EventoDominio>> eventosTratados;
	private Map<String, String> notificacoesTratadas;

	protected void eventoEsperado(
			Class<? extends EventoDominio> tipoEventoDominio) {
		this.eventoEsperado(tipoEventoDominio, 1);
	}

	protected void eventoEsperado(
			Class<? extends EventoDominio> tipoEventoDominio, int umTotal) {
		int count = 0;

		for (Class<? extends EventoDominio> tipo : this.eventosTratados) {
			if (tipo == tipoEventoDominio) {
				++count;
			}
		}

		if (count != umTotal) {
			throw new IllegalStateException("Esperados " + umTotal + " "
					+ tipoEventoDominio.getSimpleName()
					+ " eventos, mas tratados " + this.eventosTratados.size()
					+ " eventos: " + this.eventosTratados);
		}
	}

	protected void eventosEsperados(int contagemEvento) {
		if (this.eventosTratados.size() != contagemEvento) {
			throw new IllegalStateException("Expected " + contagemEvento
					+ " eventos, mas tratados " + this.eventosTratados.size()
					+ " eventos: " + this.eventosTratados);
		}
	}

	protected void notificacaoEsperada(
			Class<? extends EventoDominio> umTipoDeNotificacao) {
		this.notificacaoEsperada(umTipoDeNotificacao, 1);
	}

	protected void notificacaoEsperada(
			Class<? extends EventoDominio> umTipoDeNotificacao, int total) {
		try {
			Thread.sleep(200L);
		} catch (InterruptedException e) {
			// ignore
		}

		int contagem = 0;

		String nomeTipoNotificacao = umTipoDeNotificacao.getName();

		for (String tipo : this.notificacoesTratadas.values()) {
			if (tipo.equals(nomeTipoNotificacao)) {
				// System.out.println("MATCHED: " + type);
				// System.out.println("WITH: " + notificationTypeName);
				++contagem;
			}
		}

		if (contagem != total) {
			throw new IllegalStateException("Esperada " + total + " "
					+ umTipoDeNotificacao.getSimpleName()
					+ " notificações, mas tratada(s) "
					+ this.notificacoesTratadas.size() + " notificações: "
					+ this.notificacoesTratadas.values());
		}
	}

	protected void notificacoesEsperadas(int umaContagemNotificacao) {
		try {
			Thread.sleep(200L);
		} catch (InterruptedException e) {
			// ignore
		}

		if (this.notificacoesTratadas.size() != umaContagemNotificacao) {
			throw new IllegalStateException("Esperada "
					+ umaContagemNotificacao + " notificações, mas tratadas "
					+ this.notificacoesTratadas.size() + " notificações: "
					+ this.notificacoesTratadas.values());
		}
	}

	protected void limparEventosTratados() {
		eventosTratados = new ArrayList<Class<? extends EventoDominio>>();
	}

	protected void limparNotificacoesTratadas() {
		eventosTratados = new ArrayList<Class<? extends EventoDominio>>();
	}
	
	@Before
	public void setUp() throws Exception {
		if (RASTREAR_NOTIFICACOES){
			Thread.sleep(100L);
	
			SlothServer.executeInProcessDetachedServer();
	
			Thread.sleep(100L);
		}

		EventoDominioPublicador.instancia().reset();

		EventoDominioPublicador.instancia().assinar(new EventoDominioAssinante<EventoDominio>() {
			@Override
			public void tratarEvento(EventoDominio eventoDominio) {
				eventosTratados.add(eventoDominio.getClass());
			}

			@Override
			public Class<EventoDominio> inscritoParaTipoEvento() {
				return EventoDominio.class;
			}
		});

		eventosTratados = new ArrayList<Class<? extends EventoDominio>>();
		notificacoesTratadas = new HashMap<String, String>();
		
		if (RASTREAR_NOTIFICACOES){
			this.appccSlothMQExchangeListener = new TestAppccSlothMQExchangeListener();
			
			//this.appccRabbitMQExchangeListener = new TestAppccRabbitMQExchangeListener();
			
			clearExchangeListeners();
			
			Thread.sleep(200L);
		}
	}
	
	protected void clearExchangeListeners() throws InterruptedException {
	    // At beginning of the test, give MQExchangeListeners time to receive
	    // messages from queues which were published by previous tests.
	    // Since RabbitMQ Java Client does not allow queue listing or cleaning
	    // all queues at once, we can just consume all messages and do
	    // nothing with them as a work-around.
	    Thread.sleep(500L);
	    
	    //this.appccRabbitMQExchangeListener.clear();
	}

	@After
	public void tearDown(){
		if (RASTREAR_NOTIFICACOES){
			//tearDownRabbitMQ();
			tearDownSlothMQ();
		}
	}

	protected void tearDownRabbitMQ(){
		this.appccRabbitMQExchangeListener.close();
	}

	protected void tearDownSlothMQ(){
		this.appccSlothMQExchangeListener.close();
		SlothClient.instance().closeAll();
	}

	private abstract class TestExchangeListener extends com.hadrion.comum.port.adapter.messaging.rabbitmq.ExchangeListener {

        public void clear() {
            eventosTratados.clear();
            notificacoesTratadas.clear();
        }

        @Override
        protected String[] listensTo() {
            return null; // receive all
        }

        @Override
        protected void filteredDispatch(String aType, String aTextMessage) {
            synchronized (notificacoesTratadas) {
                NotificationReader notification = new NotificationReader(aTextMessage);
                notificacoesTratadas.put(notification.notificationIdAsString(), aType);
            }
        }
    }
	
	protected class TestAppccRabbitMQExchangeListener extends TestExchangeListener {
        @Override
        protected String exchangeName() {
            return Exchanges.APPCC_EXCHANGE_NAME;
        }
    }
	
	protected class TestAppccSlothMQExchangeListener
			extends
			com.hadrion.comum.port.adapter.messaging.slothmq.ExchangeListener {

		TestAppccSlothMQExchangeListener() {
			super();
		}

		@Override
		protected String exchangeName() {
			return Exchanges.APPCC_EXCHANGE_NAME;
		}

		@Override
		protected void filteredDispatch(String aType, String aTextMessage) {
			synchronized (notificacoesTratadas) {
				NotificationReader notification = new NotificationReader(
						aTextMessage);
				notificacoesTratadas.put(notification.notificationIdAsString(),
						aType);
			}
		}

		@Override
		protected String[] listensTo() {
			return null; // receive all
		}

		@Override
		protected String name() {
			return this.getClass().getName();
		}
	}
	
}
