package com.hadrion.comum.evento;

import java.util.List;

import com.hadrion.comum.dominio.modelo.EventoDominio;

public class EventStoreJpa implements EventStore {

	@Override
	public List<StoredEvent> allStoredEventsBetween(long aLowStoredEventId,
			long aHighStoredEventId) {
		
		return null;
	}

	@Override
	public List<StoredEvent> todosEventosArmazenadosDesde(long aStoredEventId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredEvent append(EventoDominio aDomainEvent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long countStoredEvents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
