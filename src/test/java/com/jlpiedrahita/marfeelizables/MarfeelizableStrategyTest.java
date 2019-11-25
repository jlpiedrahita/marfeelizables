package com.jlpiedrahita.marfeelizables;

import com.jlpiedrahita.marfeelizables.service.MarfeelizableStrategy;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarfeelizableStrategyTest {
    @Test
    void testHeadKeywordsArePresent() throws MalformedURLException {
        MarfeelizableStrategy s = MarfeelizableStrategy.htmlHeadKeywordsStrategy("foo", "bar", "baz");
        String html = "<html><head><title>fizz foo buzz</title></head><body></body></html>";
        boolean isMarfeelizable = s.isMarfeelizable(new URL("https://foo.com"), html);
        assertTrue(isMarfeelizable);
    }

    @Test
    void testHeadKeywordsAreCaseInsensitive() throws MalformedURLException {
        MarfeelizableStrategy s = MarfeelizableStrategy.htmlHeadKeywordsStrategy("FoO", "bar", "baz");
        String html = "<html><head><title>fizz foo buzz</title></head><body></body></html>";
        boolean isMarfeelizable = s.isMarfeelizable(new URL("https://foo.com"), html);
        assertTrue(isMarfeelizable);
    }

    @Test
    void testHeadKeywordsAreNotPresent() throws MalformedURLException {
        MarfeelizableStrategy s = MarfeelizableStrategy.htmlHeadKeywordsStrategy("foo", "bar", "baz");
        String html = "<html><head><title>fizz buzz</title></head><body></body></html>";
        boolean isMarfeelizable = s.isMarfeelizable(new URL("https://foo.com"), html);
        assertFalse(isMarfeelizable);
    }

    @Test
    void testHeadEmptyKeywords() throws MalformedURLException {
        MarfeelizableStrategy s = MarfeelizableStrategy.htmlHeadKeywordsStrategy("foo", "bar", "baz");
        String html = "<html><head><title></title></head><body></body></html>";
        boolean isMarfeelizable = s.isMarfeelizable(new URL("https://foo.com"), html);
        assertFalse(isMarfeelizable);
    }

    @Test
    void testNoKeywords() throws MalformedURLException {
        MarfeelizableStrategy s = MarfeelizableStrategy.htmlHeadKeywordsStrategy();
        String html = "<html><head><title>fizz buzz foo</title></head><body></body></html>";
        boolean isMarfeelizable = s.isMarfeelizable(new URL("https://foo.com"), html);
        assertFalse(isMarfeelizable);
    }
}
