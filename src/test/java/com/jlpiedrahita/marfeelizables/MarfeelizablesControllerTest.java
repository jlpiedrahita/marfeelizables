package com.jlpiedrahita.marfeelizables;

import com.jlpiedrahita.marfeelizables.config.App;
import com.jlpiedrahita.marfeelizables.config.Persistence;
import com.jlpiedrahita.marfeelizables.config.Web;
import com.jlpiedrahita.marfeelizables.controller.MarfeelizablesController;
import com.jlpiedrahita.marfeelizables.model.Website;
import com.jlpiedrahita.marfeelizables.service.MarfeelizablesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URL;
import java.util.Collections;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Web.class, App.class, Persistence.class})
class MarfeelizablesControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Mock
    private MarfeelizablesService marfeelizablesService;

    @InjectMocks
    private MarfeelizablesController marfeelizableWebsitesController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(marfeelizableWebsitesController).build();
    }

    @Test
    void testEmptyResponse() throws Exception {
        this.mockMvc.perform(get("/marfeelizables")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void testResponseJSONContentType() throws Exception {
        this.mockMvc.perform(get("/marfeelizables")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void testExpectedJSONResponseFormat() throws Exception {
        Website testWebsite = new Website(0L, "marfeel.com", true, new Date(332294400000L), Website.VisitStatus.SUCCESS);
        when(marfeelizablesService.listWebsites()).thenReturn(Collections.singletonList(testWebsite));

        String expectedJSONResponse = "[{'id':0,'visitStatus':'SUCCESS','visitDate':'13/07/1980 12:00:00 UTC','marfeelizable':true,'url':'marfeel.com'}]";
        this.mockMvc.perform(get("/marfeelizables")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json(expectedJSONResponse));
    }

    @Test
    void testGetCallsMarfeelizablesService() throws Exception {
        this.mockMvc.perform(get("/marfeelizables")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        verify(marfeelizablesService, times(1)).listWebsites();
    }

    @Test
    void testHandlesInvalidRequest() throws Exception {
        this.mockMvc.perform(post("/marfeelizables")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content("["))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testAcceptsRequest() throws Exception {
        this.mockMvc.perform(post("/marfeelizables")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content("[{\"url\": \"google.com\"}]"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testPostCallsMarfeelizablesService() throws Exception {
        URL testURL = new URL("http://elpais.com");
        this.mockMvc.perform(post("/marfeelizables")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content("[{\"url\": \"elpais.com\"}]"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(marfeelizablesService, times(1))
                .checkWebsites(Collections.singletonList(testURL));
    }
}
