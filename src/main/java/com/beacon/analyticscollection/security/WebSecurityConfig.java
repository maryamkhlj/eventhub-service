package com.beacon.analyticscollection.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	// Admin, SuperAdmin, Supervisor, Therapist, Intake, Content, System, Client, CaseWorker
    	
        httpSecurity
	        // we don't need CSRF because our token is invulnerable
	        .csrf().disable()
	
	        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
	
	        // don't create session
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	
	        .authorizeRequests()
	        .antMatchers(HttpMethod.OPTIONS).permitAll()
	        .anyRequest().authenticated();

        // Custom JWT based security filter
        httpSecurity
            .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity
            .headers()
            .cacheControl();
    }
    


    @Override
    public void configure(WebSecurity web) throws Exception {
        // configuring here URLs for which security filters will be disabled 
    	web
    		.ignoring()
    		.antMatchers("/analytics/open/**");
    }


}
