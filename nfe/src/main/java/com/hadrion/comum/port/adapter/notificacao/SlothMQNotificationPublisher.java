//   Copyright 2012,2013 Vaughn Vernon
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package com.hadrion.comum.port.adapter.notificacao;

import java.util.ArrayList;
import java.util.List;

import com.hadrion.comum.dominio.modelo.EventoDominio;
import com.hadrion.comum.evento.EventStore;
import com.hadrion.comum.evento.StoredEvent;
import com.hadrion.comum.notificacao.Notification;
import com.hadrion.comum.notificacao.NotificationPublisher;
import com.hadrion.comum.notificacao.NotificationSerializer;
import com.hadrion.comum.notificacao.PublishedNotificationTracker;
import com.hadrion.comum.notificacao.PublishedNotificationTrackerStore;
import com.hadrion.comum.port.adapter.messaging.slothmq.ExchangePublisher;

public class SlothMQNotificationPublisher implements NotificationPublisher {

    private EventStore eventStore;
    private String exchangeName;
    private ExchangePublisher exchangePublisher;
    private PublishedNotificationTrackerStore publishedNotificationTrackerStore;

    public SlothMQNotificationPublisher(
            EventStore anEventStore,
            PublishedNotificationTrackerStore aPublishedNotificationTrackerStore,
            Object aMessagingLocator) {

        super();

        this.setEventStore(anEventStore);
        this.setExchangeName((String) aMessagingLocator);
        this.setExchangePublisher(new ExchangePublisher(this.exchangeName()));
        this.setPublishedNotificationTrackerStore(aPublishedNotificationTrackerStore);
    }

    @Override
    public void publishNotifications() {
        PublishedNotificationTracker publishedNotificationTracker =
                this.publishedNotificationTrackerStore().publishedNotificationTracker();

        List<Notification> notifications =
            this.listUnpublishedNotifications(
                    publishedNotificationTracker.mostRecentPublishedNotificationId());

        try {
            for (Notification notification : notifications) {
                this.publish(notification);
            }

            this.publishedNotificationTrackerStore()
                .trackMostRecentPublishedNotification(
                    publishedNotificationTracker,
                    notifications);
        } catch (Exception e) {
            System.out.println("SLOTH: NotificationPublisher problem: " + e.getMessage());
        }
    }

    @Override
    public boolean internalOnlyTestConfirmation() {
        throw new UnsupportedOperationException("Not supported by production implementation.");
    }

    private EventStore eventStore() {
        return this.eventStore;
    }

    private void setEventStore(EventStore anEventStore) {
        this.eventStore = anEventStore;
    }

    private String exchangeName() {
        return this.exchangeName;
    }

    private void setExchangeName(String anExchangeName) {
        this.exchangeName = anExchangeName;
    }

    private ExchangePublisher exchangePublisher() {
        return exchangePublisher;
    }

    private void setExchangePublisher(ExchangePublisher anExchangePublisher) {
        this.exchangePublisher = anExchangePublisher;
    }

    private List<Notification> listUnpublishedNotifications(
            long aMostRecentPublishedMessageId) {
        List<StoredEvent> storedEvents =
            this.eventStore().allStoredEventsSince(aMostRecentPublishedMessageId);

        List<Notification> notifications =
            this.notificationsFrom(storedEvents);

        return notifications;
    }

    private List<Notification> notificationsFrom(List<StoredEvent> aStoredEvents) {
        List<Notification> notifications =
            new ArrayList<Notification>(aStoredEvents.size());

        for (StoredEvent storedEvent : aStoredEvents) {
            EventoDominio domainEvent = storedEvent.toDomainEvent();

            Notification notification =
                new Notification(storedEvent.eventId(), domainEvent);

            notifications.add(notification);
        }

        return notifications;
    }

    private void publish(Notification aNotification) {

        String notification =
            NotificationSerializer
                .instance()
                .serialize(aNotification);

        this.exchangePublisher().publish(aNotification.typeName(), notification);
    }

    private PublishedNotificationTrackerStore publishedNotificationTrackerStore() {
        return publishedNotificationTrackerStore;
    }

    private void setPublishedNotificationTrackerStore(PublishedNotificationTrackerStore publishedNotificationTrackerStore) {
        this.publishedNotificationTrackerStore = publishedNotificationTrackerStore;
    }
}
