package com.jlpiedrahita.marfeelizables.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;

/**
 * An strategy to tell whether a website is marfeelizable or not.
 */
@FunctionalInterface
public interface MarfeelizableStrategy {

    Logger log = LoggerFactory.getLogger(MarfeelizableStrategy.class);

    boolean isMarfeelizable(URL url, String html);

    /**
     * Strategy that checks whether certain keywords are present within the text of the HTML {@code HEAD} tag.
     *
     * @param keywords keywords to check
     * @return Whether the site is marfeelizable
     */
    static MarfeelizableStrategy htmlHeadKeywordsStrategy(String... keywords) {
        return (url, html) -> {
            String siteTitle = Jsoup.parse(html).title();
            boolean marfeelizable = Arrays
                    .stream(keywords)
                    .map(String::toLowerCase)
                    .anyMatch(siteTitle.toLowerCase()::contains);

            log.info("{} | marfeelizable: {} | title: {}", url, marfeelizable, siteTitle);
            return marfeelizable;
        };
    }

    /**
     * Strategy that checks whether certain keywords are present within the {@code content} attribute of
     * the HTML {@code <meta name=keywords/>} tag.
     *
     * @param keywords keywords to check
     * @return Whether the site is marfeelizable
     */
    static MarfeelizableStrategy htmlMetaKeywordsStrategy(String... keywords) {
        return (url, html) -> {
            Elements metaTags = Jsoup.parse(html).getElementsByTag("meta");
            Element keywordsTag = metaTags.select("[name=keywords]").first();

            if (keywordsTag == null) {
                log.info("{} | marfeelizable: false | no meta keywords", url);
                return false;
            }

            String content = keywordsTag.attr("content");
            boolean marfeelizable = Arrays.stream(keywords).anyMatch(content::contains);
            log.info("{} | marfeelizable: {} | meta keywords: {}", url, marfeelizable, content);
            return marfeelizable;
        };
    }

}