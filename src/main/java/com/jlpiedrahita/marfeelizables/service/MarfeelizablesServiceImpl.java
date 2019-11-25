package com.jlpiedrahita.marfeelizables.service;

import com.jlpiedrahita.marfeelizables.model.Website;
import com.jlpiedrahita.marfeelizables.repository.WebsiteRepository;
import com.jlpiedrahita.marfeelizables.service.http.HttpClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.List;

import static com.jlpiedrahita.marfeelizables.model.Website.VisitStatus;

@Service
public class MarfeelizablesServiceImpl implements MarfeelizablesService {

    private static Logger log = LoggerFactory.getLogger(MarfeelizablesServiceImpl.class);

    private final HttpClientService httpClientService;
    private final WebsiteRepository websiteRepository;
    private final MarfeelizableStrategy marfeelizableStrategy;

    public MarfeelizablesServiceImpl(
            MarfeelizableStrategy marfeelizableStrategy,
            HttpClientService httpClientService,
            WebsiteRepository websiteRepository) {
        this.marfeelizableStrategy = marfeelizableStrategy;
        this.httpClientService = httpClientService;
        this.websiteRepository = websiteRepository;
    }

    @Override
    public List<Website> listWebsites() {
        return websiteRepository.findAll();
    }

    /**
     * Checks whether a list of websites are marfeelizables or not.
     * <p>
     * This implementation visits the websites concurrently and persists the result of the
     * test immediately for each website so the results are available to the user ASAP via
     * the {@link #listWebsites() listWebsites} method.
     *
     * @param urls list of websites to check
     */
    @Override
    public void checkWebsites(List<URL> urls) {
        for (URL url : urls) {
            this.httpClientService.htmlContentFromURL(url).thenAccept(html -> {
                this.websiteRepository.save(
                        new Website(url.toString(),
                                this.marfeelizableStrategy.isMarfeelizable(url, html),
                                new Date(),
                                VisitStatus.SUCCESS
                        )
                );
            }).exceptionally(exception -> {
                this.websiteRepository.save(
                        new Website(
                                url.toString(),
                                false,
                                new Date(),
                                VisitStatus.ERROR // add extra info using exception.getCause()/getMessage()
                        )
                );
                log.debug("Error reaching website {}: {}", url, exception.getMessage());
                return null;
            });
        }
    }
}
