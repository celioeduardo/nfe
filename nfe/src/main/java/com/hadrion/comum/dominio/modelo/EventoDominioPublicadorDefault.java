package com.hadrion.comum.dominio.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class EventoDominioPublicadorDefault implements EventoDominioPublicador,
		ApplicationEventPublisherAware {

	private ApplicationEventPublisher applicationEventPublisher;

	private boolean publicando;

	@SuppressWarnings("rawtypes")
	private List assinantes;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends EventoDominio> void publicar(final T umEventoDominio) {
		
		applicationEventPublisher.publishEvent(new EventoDominioSpring(
				umEventoDominio, this));

		if (!this.estaPublicando() && this.temAssinantes()) {

			try {
				this.setPublicando(true);

				Class<?> eventType = umEventoDominio.getClass();

				//@SuppressWarnings("unchecked")
				List<EventoDominioAssinante<T>> todosAssinantes = this
						.assinantes();

				for (EventoDominioAssinante<T> assinante : todosAssinantes) {
					Class<?> inscritoParaTipo = assinante
							.inscritoParaTipoEvento();

					if (eventType == inscritoParaTipo
							|| inscritoParaTipo == EventoDominio.class) {
						assinante.tratarEvento(umEventoDominio);
					}
				}

			} finally {
				this.setPublicando(false);
			}
		}

	}

	@Override
	public void publicarTodos(Collection<EventoDominio> eventosDominio) {
		for (EventoDominio eventoDominio : eventosDominio) {
			this.publicar(eventoDominio);
		}
	}

	@Override
	public void reset() {
		if (!this.estaPublicando()) {
			this.setAssinantes(null);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends EventoDominio> void assinar(EventoDominioAssinante<T> umAssinante) {
		if (!this.estaPublicando()) {
			this.garantirListaAssinantes();
			this.assinantes().add(umAssinante);
		}
	}

	private EventoDominioPublicadorDefault() {
		super();

		this.setPublicando(false);
		this.garantirListaAssinantes();
	}

	@SuppressWarnings("rawtypes")
	private void garantirListaAssinantes() {
		if (!this.temAssinantes()) {
			this.setAssinantes(new ArrayList());
		}
	}

	private boolean estaPublicando() {
		return this.publicando;
	}

	private void setPublicando(boolean aFlag) {
		this.publicando = aFlag;
	}

	private boolean temAssinantes() {
		return this.assinantes() != null;
	}

	@SuppressWarnings("rawtypes")
	private List assinantes() {
		return this.assinantes;
	}

	@SuppressWarnings("rawtypes")
	private void setAssinantes(List listaAssinantes) {
		this.assinantes = listaAssinantes;
	}

	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;

	}
}
