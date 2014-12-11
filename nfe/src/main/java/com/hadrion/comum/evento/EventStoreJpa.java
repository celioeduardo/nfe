package com.hadrion.comum.evento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hadrion.comum.dominio.modelo.EventoDominio;

@Repository
@Transactional
public class EventStoreJpa implements EventStore {
	
	@Autowired
	private EventStoreSpringData repositorio;
	
	@Override
	public List<StoredEvent> allStoredEventsBetween(long aLowStoredEventId,
			long aHighStoredEventId) {
		return repositorio.findByEventIdBetweenOrderByEventIdAsc(aLowStoredEventId,aHighStoredEventId);
	}

	@Override
	public List<StoredEvent> todosEventosArmazenadosDesde(long aStoredEventId) {
		return repositorio.findByEventIdGreaterThanOrderByEventIdAsc(aStoredEventId);
	}

	@Override
	public StoredEvent append(EventoDominio eventoDominio) {
		
		String eventSerialization = EventSerializer.instance().serialize(
				eventoDominio);
		
		StoredEvent storedEvent = new StoredEvent(eventoDominio.getClass()
				.getName(), eventoDominio.ocorridoEm(), eventSerialization);
		return repositorio.save(storedEvent);
	}

	@Override
	public void close() {
		
	}

	@Override
	public long countStoredEvents() {
		return repositorio.count();
	}

}
