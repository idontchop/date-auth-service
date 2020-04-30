package com.idontchop.dateauthservice.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.idontchop.dateauthservice.services.UserDetailServiceImpl;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception  {
		auth.userDetailsService(this.userDetailsService)
			.passwordEncoder( passwordEncoder() );
	}
	
	protected void configure ( HttpSecurity http ) throws Exception {
		
		http
			.cors()
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.antMatcher("/**")
			.authorizeRequests()
				.antMatchers("/", "/login**")
				.permitAll()
			.anyRequest()
				.authenticated()
				.and()
			.logout()
				.logoutSuccessUrl("/")
				.permitAll()
			.and()
				.csrf()
				.disable()
			.httpBasic()
				.and()
			.formLogin()
				.disable();
			
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    CorsConfigurationSource corsConfigurationSource() 
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost","https://idontchop.com","https://www.idontchop.com"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
