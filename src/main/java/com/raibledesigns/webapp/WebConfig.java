package com.raibledesigns.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("com.raibledesigns.webapp")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();
        exceptionMappings.put("org.springframework.dao.DataAccessException", "dataAccessFailure");
        exceptionResolver.setExceptionMappings(exceptionMappings);

        return exceptionResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(2097152);
        return multipartResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("ApplicationResources");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/activeUsers").setViewName("admin/activeUsers");
        registry.addViewController("/home").setViewName("home");
    }


    /*public ValidatorFactory validatorFactory() {
        DefaultValidatorFactory factory = new DefaultValidatorFactory();
        factory.setValidationConfigLocations(new Resource[]{
                new ClassPathResource("/WEB-INF/validation.xml"),
                new ClassPathResource("/WEB-INF/validation-rules.xml"),
                new ClassPathResource("/WEB-INF/validation-rules-custom.xml")
        });
        return factory;
    }

    @Bean
    public DefaultBeanValidator beanValidator() {
        DefaultBeanValidator validator = new DefaultBeanValidator();
        validator.setValidatorFactory(validatorFactory());
        return validator;
    }*/
}

