package com.hadrion.comum.evento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStoreSpringData extends JpaRepository<StoredEvent, Long>{

	List<StoredEvent> findByEventIdBetweenOrderByEventIdAsc(long aLowStoredEventId,
			long aHighStoredEventId);

	List<StoredEvent> findByEventIdGreaterThanOrderByEventIdAsc(
			long aStoredEventId);

}
