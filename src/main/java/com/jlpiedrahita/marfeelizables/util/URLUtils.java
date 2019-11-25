package com.jlpiedrahita.marfeelizables.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * <b>SEVERAL</b> utility methods to deal with URLs
 */
public class URLUtils {

    private static final String HTTP_PREFIX = "http://";
    private static final String HTTPS_PREFIX = "https://";

    /**
     * Normalizes an URL. Tries to check for a valid protocol (http or https) and if it's not present,
     * uses HTTP as the default one.
     *
     * @param urlString to normalize
     * @return normalized URL
     * @throws MalformedURLException if the string can not be converted to a valid URL
     */
    public static URL normalize(String urlString) throws MalformedURLException {
        if (!urlString.startsWith(HTTP_PREFIX) && !urlString.startsWith(HTTPS_PREFIX)) {
            urlString = HTTP_PREFIX + urlString;
        }
        return new URL(urlString);
    }
}
