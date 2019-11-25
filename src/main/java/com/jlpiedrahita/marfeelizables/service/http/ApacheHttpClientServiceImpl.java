package com.jlpiedrahita.marfeelizables.service.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.concurrent.CompletableFuture;

@Service
public class ApacheHttpClientServiceImpl implements HttpClientService {

    private static final String MARFEELIZABLES_HTTP_CLIENT_USER_AGENT = "MarfeelizablesBot/1.0 (+https://www.marfeel.com)";
    private static Logger log = LoggerFactory.getLogger(ApacheHttpClientServiceImpl.class);

    @Async("httpClientServiceExecutor")
    public CompletableFuture<String> htmlContentFromURL(URL url) {

        log.info("Getting content from website at URL: {}", url);

        CloseableHttpClient httpclient = HttpClients
                .custom()
                .setUserAgent(MARFEELIZABLES_HTTP_CLIENT_USER_AGENT)
                .disableCookieManagement()
                .build();

        HttpGet httpGet = new HttpGet(url.toString());

        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);

            log.info("Successfully got content for website: {}", url);

            return CompletableFuture.completedFuture(content);
        } catch (Exception e) {
            log.info("Error getting content for website: {}. Error: ", url, e.getCause());
            throw new RuntimeException(e);
        }
    }
}
