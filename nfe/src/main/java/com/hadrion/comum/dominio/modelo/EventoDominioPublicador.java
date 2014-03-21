package com.hadrion.comum.dominio.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EventoDominioPublicador {

    private static final ThreadLocal<EventoDominioPublicador> instance = new ThreadLocal<EventoDominioPublicador>() {
        protected EventoDominioPublicador initialValue() {
            return new EventoDominioPublicador();
        }
    };

    private boolean publicando;

    @SuppressWarnings("rawtypes")
    private List assinantes;

    public static EventoDominioPublicador instancia() {
        return instance.get();
    }

    public <T> void publicar(final T umEventoDominio) {
        if (!this.estaPublicando() && this.temAssinantes()) {

            try {
                this.setPublicando(true);

                Class<?> eventType = umEventoDominio.getClass();

                @SuppressWarnings("unchecked")
                List<EventoDominioAssinante<T>> todosAssinantes = this.assinantes();

                for (EventoDominioAssinante<T> assinante : todosAssinantes) {
                    Class<?> inscritoParaTipo = assinante.inscritoParaTipoEvento();

                    if (eventType == inscritoParaTipo || inscritoParaTipo == EventoDominio.class) {
                        assinante.tratarEvento(umEventoDominio);
                    }
                }

            } finally {
                this.setPublicando(false);
            }
        }
    }

    public void publicarTodos(Collection<EventoDominio> eventosDominio) {
        for (EventoDominio eventoDominio : eventosDominio) {
            this.publicar(eventoDominio);
        }
    }

    public void reset() {
        if (!this.estaPublicando()) {
            this.setAssinantes(null);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void assinar(EventoDominioAssinante<T> umAssinante) {
        if (!this.estaPublicando()) {
            this.garantirListaAssinantes();
            this.assinantes().add(umAssinante);
        }
    }

    private EventoDominioPublicador() {
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
}
