package com.raibledesigns.webapp;

import com.opensymphony.sitemesh.webapp.SiteMeshFilter;
import com.raibledesigns.webapp.filter.LocaleFilter;
import com.raibledesigns.webapp.jsp.EscapeXmlELResolverListener;
import com.raibledesigns.webapp.listener.StartupListener;
import com.raibledesigns.webapp.listener.UserCounterListener;
import net.sf.navigator.menu.MenuContextListener;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.displaytag.filter.ResponseOverrideFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;
import ro.isdc.wro.http.WroFilter;

import javax.servlet.*;
import java.util.EnumSet;

public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        container.setInitParameter("javax.servlet.jsp.jstl.fmt.localizationContext", "ApplicationResources");
        container.setInitParameter("javax.servlet.jsp.jstl.fmt.fallbackLocale", "en");
        container.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
        container.setInitParameter("contextConfigLocation",
                "com.raibledesigns.config.ResourcesConfig,com.raibledesigns.config.JpaConfig," +
                "com.raibledesigns.config.ServiceConfig,com.raibledesigns.config.SecurityConfig");

        FilterRegistration.Dynamic encodingFilter = container.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic exportFilter = container.addFilter("exportFilter", ResponseOverrideFilter.class);
        exportFilter.addMappingForUrlPatterns(null, false, "/app/*");

        FilterRegistration.Dynamic localeFilter = container.addFilter("localeFilter", LocaleFilter.class);
        localeFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic rewriteFilter = container.addFilter("rewriteFilter", UrlRewriteFilter.class);
        rewriteFilter.setInitParameter("logLevel", "commons");
        rewriteFilter.setInitParameter("confReloadCheckInterval", "-1");
        rewriteFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic securityFilter = container.addFilter("securityFilter", DelegatingFilterProxy.class);
        securityFilter.setInitParameter("targetBeanName", "springSecurityFilterChain");
        securityFilter.addMappingForUrlPatterns(EnumSet.of(
                DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");

        FilterRegistration.Dynamic sitemeshFilter = container.addFilter("sitemeshFilter", SiteMeshFilter.class);
        sitemeshFilter.addMappingForUrlPatterns(EnumSet.of(
                DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/*");

        FilterRegistration.Dynamic wroFilter = container.addFilter("wroFilter", WroFilter.class);
        wroFilter.addMappingForUrlPatterns(EnumSet.of(
                DispatcherType.REQUEST, DispatcherType.FORWARD), false, "/assets/*");

        ServletRegistration.Dynamic cxfServlet = container.addServlet("CXFServlet", CXFServlet.class);
        cxfServlet.addMapping("/services/*");

        container.addListener(ContextLoaderListener.class);
        container.addListener(StartupListener.class);
        container.addListener(UserCounterListener.class);
        container.addListener(EscapeXmlELResolverListener.class);
        container.addListener(MenuContextListener.class);

    }
}
