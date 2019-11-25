package com.jlpiedrahita.marfeelizables.service;

import com.jlpiedrahita.marfeelizables.model.Website;

import java.net.URL;
import java.util.List;

public interface MarfeelizablesService {
    /**
     * @return the current list of marfeelizable websites
     */
    List<Website> listWebsites();

    /**
     * Checks whether a list of websites identified by their urls are marfeelizables or not.
     * <p>
     * The current algorithm checks the presence of the "news" and "noticias" keywords
     * within the {@code <HEAD>} html tag.
     *
     * @param urls list of websites to check
     */
    void checkWebsites(List<URL> urls);
}
