package com.raibledesigns.config;

import com.raibledesigns.service.MailEngine;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.util.Properties;

@EnableTransactionManagement
@EnableAspectJAutoProxy
@Configuration
@ComponentScan("com.raibledesigns.service")
public class ServiceConfig {

    @Bean
    @Autowired
    public MailEngine mailEngine(VelocityEngine velocityEngine) {
        MailEngine mailEngine = new MailEngine();
        mailEngine.setVelocityEngine(velocityEngine);
        mailEngine.setFrom("${mail.default.from}");
        return mailEngine;
    }

    @Bean
    public JavaMailSenderImpl mailSender(@Value("${mail.host}") String mailHost) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setDefaultEncoding("UTF-8");
        return mailSender;
    }

    @Bean
    public VelocityEngineFactoryBean velocityEngine() {
        VelocityEngineFactoryBean velocityEngineFactoryBean = new VelocityEngineFactoryBean();
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty("velocimacro.library", "");
        velocityEngineFactoryBean.setVelocityProperties(properties);
        return velocityEngineFactoryBean;
    }

    @Bean
    @Scope("prototype")
    public SimpleMailMessage mailMessage(@Value("${mail.default.from}") String defaultFrom) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(defaultFrom);
        return mailMessage;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "passwordTokenEncoder")
    public PasswordEncoder passwordTokenEncoder() {
        return new StandardPasswordEncoder();
    }
}
