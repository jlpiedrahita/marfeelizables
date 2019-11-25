package com.jlpiedrahita.marfeelizables.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.net.URL;

/**
 * <b>Naive</b> Jackson URL deserializer that tries to "normalize" URL strings received by the Marfeelizables service.
 * Specifically tries to check for a valid protocol (http or https) and if it's not present, uses HTTP as
 * the default one.
 */
public class JsonURLDeserializer extends JsonDeserializer<URL> {

    @Override
    public URL deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String urlString = p.getValueAsString(p.getCurrentName());
        return URLUtils.normalize(urlString);
    }
}
