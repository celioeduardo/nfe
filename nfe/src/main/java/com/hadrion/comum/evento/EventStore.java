package com.hadrion.comum.evento;

import java.util.List;

import com.hadrion.comum.dominio.modelo.EventoDominio;

public interface EventStore {

    public List<StoredEvent> allStoredEventsBetween(long aLowStoredEventId, long aHighStoredEventId);

    public List<StoredEvent> todosEventosArmazenadosDesde(long aStoredEventId);

    public StoredEvent append(EventoDominio aDomainEvent);

    public void close();

    public long countStoredEvents();
}
