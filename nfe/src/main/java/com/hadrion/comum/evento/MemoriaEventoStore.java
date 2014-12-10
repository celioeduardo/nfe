package com.hadrion.comum.evento;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hadrion.comum.dominio.modelo.EventoDominio;

@Repository
//TODO - Converter JpaEventStore
public class MemoriaEventoStore implements EventStore{

	private List<StoredEvent> storedEvents;

	public MemoriaEventoStore() {
		storedEvents = new ArrayList<StoredEvent>();
	}

	@Override
	public List<StoredEvent> allStoredEventsBetween(long aLowStoredEventId,
			long aHighStoredEventId) {

		List<StoredEvent> events = new ArrayList<StoredEvent>();

		for (StoredEvent storedEvent : this.storedEvents) {
			if (storedEvent.eventId() >= aLowStoredEventId
					&& storedEvent.eventId() <= aHighStoredEventId) {
				events.add(storedEvent);
			}
		}

		return events;
	}

	@Override
	public List<StoredEvent> todosEventosArmazenadosDesde(long aStoredEventId) {
		List<StoredEvent> events = new ArrayList<StoredEvent>();

		for (StoredEvent storedEvent : this.storedEvents) {
			if (storedEvent.eventId() > aStoredEventId) {
				events.add(storedEvent);
			}
		}

		return events;
	}

	@Override
	public synchronized StoredEvent append(EventoDominio eventoDominio) {
		String eventSerialization = EventSerializer.instance().serialize(
				eventoDominio);

		StoredEvent storedEvent = new StoredEvent(eventoDominio.getClass()
				.getName(), eventoDominio.ocorridoEm(), eventSerialization,
				this.storedEvents.size() + 1);

		this.storedEvents.add(storedEvent);

		return storedEvent;
	}

	@Override
	public void close() {
	
	}

	@Override
	public long countStoredEvents() {
		return this.storedEvents.size();
	}
	

}
