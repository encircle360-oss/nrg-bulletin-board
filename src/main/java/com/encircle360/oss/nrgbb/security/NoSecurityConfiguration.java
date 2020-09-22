package com.encircle360.oss.nrgbb.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;

@Configuration
@EnableWebSecurity
@Profile("insecure")
public class NoSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
            .csrf().disable()
            .cors().and()
            .sessionManagement()
            .sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy())
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .anonymous();
    }
}
