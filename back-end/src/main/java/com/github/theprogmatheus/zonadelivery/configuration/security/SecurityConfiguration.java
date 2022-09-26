package com.github.theprogmatheus.zonadelivery.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.github.theprogmatheus.zonadelivery.configuration.security.jwt.JWTAuthenticationFilter;
import com.github.theprogmatheus.zonadelivery.configuration.security.jwt.JWTAuthorizationFilter;
import com.github.theprogmatheus.zonadelivery.services.UserAccountService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "bearer";
	public static final long TOKEN_EXPIRATION_TIME = 43_200_000; // 12 horas
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTH_REGISTER_URL = "/auth/register";
	public static final String AUTH_LOGIN_URL = "/auth/login";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserAccountService userAccountService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// dizemos que queremos usar as configurações dos CORS
		http.cors()

				.and()

				// libera todo o acesso ao /auth/login
				.authorizeRequests().antMatchers(HttpMethod.POST, AUTH_REGISTER_URL, AUTH_LOGIN_URL).permitAll()

				// qualquer outro acesso tem que estar autenticado
				.anyRequest().authenticated().and()

				// adiciona os filtros que nós criamos
				.addFilter(new JWTAuthenticationFilter(this.authenticationManager))
				.addFilter(new JWTAuthorizationFilter(this.authenticationManager, this.userAccountService))

				// nós não precisamos criar sessões
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				// desabilitamos o csrf
				.and().csrf().disable();

		return http.build();
	}

}
