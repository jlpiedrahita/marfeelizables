package com.jlpiedrahita.marfeelizables.controller;

import com.jlpiedrahita.marfeelizables.model.MarfeelizableRequest;
import com.jlpiedrahita.marfeelizables.model.Website;
import com.jlpiedrahita.marfeelizables.service.MarfeelizablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MarfeelizablesController {

    private static final String REST_RESOURCE = "marfeelizables";
    private static Logger log = LoggerFactory.getLogger(MarfeelizablesController.class);

    private final MarfeelizablesService marfeelizablesService;

    public MarfeelizablesController(MarfeelizablesService marfeelizablesService) {
        this.marfeelizablesService = marfeelizablesService;
    }

    @GetMapping(
            value = REST_RESOURCE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public List<Website> getMarfeelizableWebsites() {
        return marfeelizablesService.listWebsites();
    }

    @PostMapping(
            value = REST_RESOURCE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void checkMarfeelizableWebsites(@RequestBody List<MarfeelizableRequest> request) {
        List<URL> urls = request.stream().map(MarfeelizableRequest::getURL).collect(Collectors.toList());
        log.debug("Checking marfeelizable websites: {}", urls);
        marfeelizablesService.checkWebsites(urls);
    }
}
