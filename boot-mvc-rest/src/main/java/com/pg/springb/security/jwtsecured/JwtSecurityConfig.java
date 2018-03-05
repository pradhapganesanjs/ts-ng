package com.citi.frontier.rest.jwtsecured;

import java.util.Arrays;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	JwtTokenProcessor tokenProcessor;
	
	@Autowired
	JwtUnauthEntryPoint entryPoint;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring()
	    		.regexMatchers("^(?!/api/).*$")
	    		.antMatchers("/api/login" 
	    					 ,"/api/logout")
	    		.antMatchers(HttpMethod.OPTIONS);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests()
				.antMatchers("/api/**").authenticated()
				.and()
        .addFilter(authFilter())
        .exceptionHandling().authenticationEntryPoint(this.entryPoint);
		
	}
	
	private JwtAuthorizationFilter authFilter() throws Exception {
		JwtAuthorizationFilter jwtFilter =  new JwtAuthorizationFilter(authenticationManager(), tokenProcessor);
		return jwtFilter;
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.toString(),HttpMethod.POST.toString(),HttpMethod.OPTIONS.toString()));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration);
		return source;
	}
}
