package com.github.theprogmatheus.zonadelivery.configuration.security.jwt;

import static com.github.theprogmatheus.zonadelivery.configuration.security.SecurityConfiguration.HEADER_STRING;
import static com.github.theprogmatheus.zonadelivery.configuration.security.SecurityConfiguration.TOKEN_PREFIX;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.theprogmatheus.zonadelivery.ZonaDeliveryApplication;
import com.github.theprogmatheus.zonadelivery.model.UserAccountModel;
import com.github.theprogmatheus.zonadelivery.services.UserAccountService;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private final UserAccountService userAccountService;

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserAccountService userAccountService) {
		super(authenticationManager);
		this.userAccountService = userAccountService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(HEADER_STRING);

		if (header != null && header.startsWith(TOKEN_PREFIX))
			SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));

		chain.doFilter(request, response);
	}

	// Reads the JWT from the Authorization header, and then uses JWT to validate
	// the token
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		try {
			String header = request.getHeader(HEADER_STRING);

			if (header != null && header.startsWith(TOKEN_PREFIX)) {

				// parse the token.
				String userId = JWT.require(Algorithm.HMAC512(System.getenv("SPRING_JWT_SECRET").getBytes())).build()
						.verify(header.replace(TOKEN_PREFIX, "")).getSubject();

				if (userId != null) {
					UserAccountModel user = this.userAccountService.findById(UUID.fromString(userId));
					if (user != null)
						return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				}

				return null;
			}

		} catch (Exception exception) {
			ZonaDeliveryApplication.log.error("Ocorreu um erro ao tentar processar a requisição. "
					+ exception.getClass().getSimpleName() + " -> " + exception.getMessage());
		}

		return null;
	}
}
