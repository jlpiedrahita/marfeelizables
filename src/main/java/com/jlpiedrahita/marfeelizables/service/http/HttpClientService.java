package com.jlpiedrahita.marfeelizables.service.http;

import java.net.URL;
import java.util.concurrent.CompletableFuture;

public interface HttpClientService {
    /**
     * Retrieves the text content (usually HTML) of a site at the specified URL
     * <p>
     * This method will retry if the site is not available or there is an error during the network connection
     *
     * @param url URL of the website to get the content to
     * @return CompletableFuture Future that will complete with an String containing the
     * contents (usually HTML) of the site at the specified URL
     */
    CompletableFuture<String> htmlContentFromURL(URL url);
}
