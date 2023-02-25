package com.bakheet.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configurable
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception {
        httpSecurity.formLogin();
        httpSecurity.authorizeRequests()
                        .antMatchers("/")
                                .permitAll();

        httpSecurity.exceptionHandling()
                        .accessDeniedPage("/403");

        httpSecurity.authorizeRequests()
                .anyRequest()
                .authenticated();

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


}
