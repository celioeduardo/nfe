package com.hadrion.comum.notificacao;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.JsonObject;
import com.hadrion.comum.media.AbstractJSONMediaReader;

public class NotificationReader extends AbstractJSONMediaReader {

    private JsonObject event;

    public NotificationReader(String aJSONNotification) {
        super(aJSONNotification);

        this.setEvent(this.representation().get("evento").getAsJsonObject());
    }

    public NotificationReader(JsonObject aRepresentationObject) {
        super(aRepresentationObject);

        this.setEvent(this.representation().get("evento").getAsJsonObject());
    }

    public BigDecimal eventBigDecimalValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : new BigDecimal(stringValue);
    }

    public Boolean eventBooleanValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Boolean.parseBoolean(stringValue);
    }

    public Date eventDateValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : new Date(Long.parseLong(stringValue));
    }

    public Double eventDoubleValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Double.parseDouble(stringValue);
    }

    public Float eventFloatValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Float.parseFloat(stringValue);
    }

    public Integer eventIntegerValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Integer.parseInt(stringValue);
    }

    public Long eventLongValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue == null ? null : Long.parseLong(stringValue);
    }

    public String eventStringValue(String... aKeys) {
        String stringValue = this.stringValue(this.event(), aKeys);

        return stringValue;
    }

    public long notificationId() {
        long notificationId = this.longValue("notificacaoId");

        return notificationId;
    }

    public String notificationIdAsString() {
        String notificationId = this.stringValue("notificacaoId");

        return notificationId;
    }

    public Date occurredOn() {
        long time = this.longValue("ocorridoEm");

        return new Date(time);
    }

    public String typeName() {
        String typeName = this.stringValue("nomeTipo");

        return typeName;
    }

    public int version() {
        int version = this.integerValue("versao");

        return version;
    }

    private JsonObject event() {
        return this.event;
    }

    private void setEvent(JsonObject anEvent) {
        this.event = anEvent;
    }
}
