package com.jlpiedrahita.marfeelizables;

import com.jlpiedrahita.marfeelizables.util.URLUtils;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URLUtilsTest {
    @Test
    void testAddsDefaultProtocol() throws MalformedURLException {
        URL url = URLUtils.normalize("marfeel.com");
        assertEquals("http://marfeel.com", url.toString());
    }

    @Test
    void testDoesNotAddDefaultProtocolIfPresent() throws MalformedURLException {
        URL httpURL = URLUtils.normalize("http://marfeel.com");
        assertEquals("http://marfeel.com", httpURL.toString());

        URL httpsURL = URLUtils.normalize("https://marfeel.com");
        assertEquals("https://marfeel.com", httpsURL.toString());
    }
}
