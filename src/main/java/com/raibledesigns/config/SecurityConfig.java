package com.raibledesigns.config;

import com.raibledesigns.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDao)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/images/**")
                .antMatchers("/styles/**")
                .antMatchers("/scripts/**")
                .antMatchers("/assets/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
            .headers()
            .frameOptions()
            .disable()
            .authorizeRequests()
            .antMatchers("/app/admin/**").hasAuthority("ROLE_ADMIN")
            .antMatchers("/app/passwordHint*").permitAll()
            .antMatchers("/app/requestRecoveryToken*").permitAll()
            .antMatchers("/app/updatePassword*").permitAll()
            .antMatchers("/app/signup*").permitAll()
            .antMatchers("/app/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
        .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .loginProcessingUrl("/j_security_check")
                .failureUrl("/login?error=true")
        .and()
            .rememberMe()
                .userDetailsService(userDao)
                .key("e37f4b31-0c45-11dd-bd0b-0800200c9a66");
    }
}
