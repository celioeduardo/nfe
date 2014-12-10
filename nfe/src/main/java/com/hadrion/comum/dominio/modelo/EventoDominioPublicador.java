package com.hadrion.comum.dominio.modelo;

import java.util.Collection;

public interface EventoDominioPublicador {

	<T extends EventoDominio> void publicar(T umEventoDominio);

	void publicarTodos(Collection<EventoDominio> eventosDominio);

	void reset();

	<T extends EventoDominio> void assinar(EventoDominioAssinante<T> umAssinante);

}