package com.hadrion.comum.dominio.modelo;

public interface EventoDominioAssinante<T> {

    public void tratarEvento(final T eventoDominio);

    public Class<T> inscritoParaTipoEvento();
}
