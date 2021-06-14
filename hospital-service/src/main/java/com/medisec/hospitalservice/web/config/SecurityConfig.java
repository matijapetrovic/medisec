package com.medisec.hospitalservice.web.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import java.io.InputStream;

@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        http
//                .headers()
//                .xssProtection()
//                .and()
//                .contentSecurityPolicy("script-src 'self'");;
//        http
//                .authorizeRequests()
//                .antMatchers("/**").hasAuthority("admin")
//                .antMatchers("/medical-record").hasAnyRole()
//                .and()
//                .requiresChannel()
//                .anyRequest()
//                .requiresSecure()
//                .and()
//                .cors()
//                .and()
//                .csrf().disable();
        super.configure(http);
        http
                .headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'");;
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/patients").hasAuthority("doctor")
                .antMatchers(HttpMethod.POST, "/api/service-log").permitAll()
                .antMatchers(HttpMethod.GET, "/api/service-log/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/service-log-alarm/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/medical-record-log/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/medical-record-log/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/medical-record-alarm/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/rule/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/rule").permitAll()
                .antMatchers(HttpMethod.GET, "/api/log-sources").hasAuthority("admin")
                .antMatchers(HttpMethod.POST, "/api/log-sources").hasAuthority("admin")
                .antMatchers("/**").hasAuthority("admin")
                .and()
                .requiresChannel()
                .anyRequest()
                .requiresSecure()
                .and()
                .cors()
                .and()
                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(keycloakAuthenticationProvider());
    }

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakConfigResolver() {

            private KeycloakDeployment keycloakDeployment;

            @Override
            public KeycloakDeployment resolve(HttpFacade.Request facade) {
                if (keycloakDeployment != null) {
                    return keycloakDeployment;
                }

                String path = "/keycloak.json";
                InputStream configInputStream = getClass().getResourceAsStream(path);

                if (configInputStream == null) {
                    throw new RuntimeException("Could not load Keycloak deployment info: " + path);
                } else {
                    keycloakDeployment = KeycloakDeploymentBuilder.build(configInputStream);
                }

                return keycloakDeployment;
            }
        };
    }
}
