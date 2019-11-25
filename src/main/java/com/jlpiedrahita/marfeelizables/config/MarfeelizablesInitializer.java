package com.jlpiedrahita.marfeelizables.config;

import com.jlpiedrahita.marfeelizables.config.App;
import com.jlpiedrahita.marfeelizables.config.Persistence;
import com.jlpiedrahita.marfeelizables.config.Web;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MarfeelizablesInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{Web.class, App.class, Persistence.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}