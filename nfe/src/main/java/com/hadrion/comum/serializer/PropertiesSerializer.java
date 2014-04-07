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

package com.hadrion.comum.serializer;

import java.util.Properties;

import com.google.gson.JsonObject;

public class PropertiesSerializer extends AbstractSerializer {

    private static PropertiesSerializer propertiesSerializer;

    public static synchronized PropertiesSerializer instance() {
        if (PropertiesSerializer.propertiesSerializer == null) {
            PropertiesSerializer.propertiesSerializer = new PropertiesSerializer(false);
        }

        return PropertiesSerializer.propertiesSerializer;
    }

    public PropertiesSerializer(boolean isCompact) {
        this(false, isCompact);
    }

    public PropertiesSerializer(boolean isPretty, boolean isCompact) {
        super(isPretty, isCompact);
    }

    public String serialize(Properties aProperties) {
        JsonObject object = new JsonObject();

        for (Object keyObj : aProperties.keySet()) {
            String key = keyObj.toString();
            String value = aProperties.getProperty(key);
            object.addProperty(key, value);
        }

        return object.getAsString();
    }
}
