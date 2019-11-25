package com.jlpiedrahita.marfeelizables.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jlpiedrahita.marfeelizables.util.JsonURLDeserializer;

import java.net.URL;

/**
 * Represents a request to check whether a website identified by URL
 * is marfeelizable or not
 */
public class MarfeelizableRequest {
    @JsonDeserialize(using = JsonURLDeserializer.class)
    private URL url;

    /**
     * @return website URL to check
     */
    public URL getURL() {
        return this.url;
    }
}
