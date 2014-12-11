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

package com.hadrion.comum.evento;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.hadrion.comum.Afirmacao;
import com.hadrion.comum.dominio.modelo.EventoDominio;

@Entity
@SequenceGenerator(name="SEQ", sequenceName="SQ_STORED_EVENT")
@Table(name="STORED_EVENT")
public class StoredEvent extends Afirmacao {
	
	@Lob
	@Column(name="BODY")
	private String eventBody;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ")
	@Column(name="ID")
	private long eventId;
	
	@Temporal(TemporalType.TIME)
	@Column(name="OCCURRED_ON")
    private Date occurredOn;
	
	@Column(name="TYPE")
    private String typeName;

    public StoredEvent(String aTypeName, Date anOccurredOn, String anEventBody) {
        this();

        this.setEventBody(anEventBody);
        this.setOccurredOn(anOccurredOn);
        this.setTypeName(aTypeName);
    }

    public StoredEvent(String aTypeName, Date anOccurredOn, String anEventBody, long anEventId) {
        this(aTypeName, anOccurredOn, anEventBody);

        this.setEventId(anEventId);
    }

    public String eventBody() {
        return this.eventBody;
    }

    public long eventId() {
        return this.eventId;
    }

    public Date occurredOn() {
        return this.occurredOn;
    }

    @SuppressWarnings("unchecked")
    public <T extends EventoDominio> T paraEventoDominio() {
        Class<T> domainEventClass = null;

        try {
            domainEventClass = (Class<T>) Class.forName(this.typeName());
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Class load error, because: "
                    + e.getMessage());
        }

        T domainEvent =
            EventSerializer
                .instance()
                .deserialize(this.eventBody(), domainEventClass);

        return domainEvent;
    }

    public String typeName() {
        return this.typeName;
    }

    @Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
            StoredEvent typedObject = (StoredEvent) anObject;
            equalObjects = this.eventId() == typedObject.eventId();
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (1237 * 233)
            + (int) this.eventId();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "StoredEvent [eventBody=" + eventBody + ", eventId=" + eventId + ", occurredOn=" + occurredOn + ", typeName="
                + typeName + "]";
    }

    public StoredEvent() {
        super();

        this.setEventId(-1);
    }

    protected void setEventBody(String anEventBody) {
        this.assertArgumentoNaoVazio(anEventBody, "The event body is required.");
        this.assertTamanhoArgumento(anEventBody, 1, 65000, "The event body must be 65000 characters or less.");

        this.eventBody = anEventBody;
    }

    protected void setEventId(long anEventId) {
        this.eventId = anEventId;
    }

    protected void setOccurredOn(Date anOccurredOn) {
        this.occurredOn = anOccurredOn;
    }

    protected void setTypeName(String aTypeName) {
        this.assertArgumentoNaoVazio(aTypeName, "The event type name is required.");
        this.assertTamanhoArgumento(aTypeName, 1, 100, "The event type name must be 100 characters or less.");

        this.typeName = aTypeName;
    }
}
