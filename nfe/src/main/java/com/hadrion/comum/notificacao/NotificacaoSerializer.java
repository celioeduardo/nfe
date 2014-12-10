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

package com.hadrion.comum.notificacao;

import com.hadrion.comum.serializer.AbstractSerializer;

public class NotificacaoSerializer extends AbstractSerializer {

    private static NotificacaoSerializer notificacaoSerializer;

    public static synchronized NotificacaoSerializer instance() {
        if (NotificacaoSerializer.notificacaoSerializer == null) {
            NotificacaoSerializer.notificacaoSerializer = new NotificacaoSerializer();
        }

        return NotificacaoSerializer.notificacaoSerializer;
    }

    public NotificacaoSerializer(boolean isCompact) {
        this(false, isCompact);
    }

    public NotificacaoSerializer(boolean isPretty, boolean isCompact) {
        super(isPretty, isCompact);
    }

    public String serialize(Notificacao aNotification) {
        String serialization = this.gson().toJson(aNotification);

        return serialization;
    }

    public <T extends Notificacao> T deserialize(String aSerialization, final Class<T> aType) {
        T notification = this.gson().fromJson(aSerialization, aType);

        return notification;
    }

    private NotificacaoSerializer() {
        this(false, false);
    }
}
