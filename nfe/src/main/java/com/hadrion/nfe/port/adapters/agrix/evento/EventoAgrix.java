package com.hadrion.nfe.port.adapters.agrix.evento;

import java.util.Date;

public class EventoAgrix {

	private Long id;
	private String type;
	private Date occurredOn;
	private String body;
	
	public EventoAgrix(Long id, String type, Date occurredOn, String body) {
		super();
		this.id = id;
		this.type = type;
		this.occurredOn = occurredOn;
		this.body = body;
	}
	
	public Long id(){
		return id;
	}
	
	public String type(){
		return type;
	}
	
	public Date occurredOn(){
		return occurredOn;
	}
	
	public String body(){
		return body;
	}
	
	@Override
    public boolean equals(Object anObject) {
        boolean equalObjects = false;

        if (anObject != null && this.getClass() == anObject.getClass()) {
        	EventoAgrix typedObject = (EventoAgrix) anObject;
            equalObjects = this.id() == typedObject.id();
        }

        return equalObjects;
    }

    @Override
    public int hashCode() {
        int hashCodeValue =
            + (1237 * 233)
            + this.id.intValue();

        return hashCodeValue;
    }

    @Override
    public String toString() {
        return "EventoAgrix [body=" + body + ", id=" + id + ", occurredOn=" + occurredOn + ", type="
                + type + "]";
    }

}
