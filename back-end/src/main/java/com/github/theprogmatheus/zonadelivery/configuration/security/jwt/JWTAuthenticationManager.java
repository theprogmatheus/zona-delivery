package com.github.theprogmatheus.zonadelivery.configuration.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.github.theprogmatheus.zonadelivery.model.UserAccountModel;
import com.github.theprogmatheus.zonadelivery.services.UserAccountService;

@Component
public class JWTAuthenticationManager implements AuthenticationManager {

	@Autowired
	private UserAccountService userAccountService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// authenticate users

		if (authentication == null)
			throw new BadCredentialsException("BCE1000");

		if (authentication.getPrincipal() == null)
			throw new BadCredentialsException("BCE1001");

		if (authentication.getCredentials() == null)
			throw new BadCredentialsException("BCE1002");

		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();

		UserAccountModel user = this.userAccountService.findByName(username);

		if (user == null)
			throw new BadCredentialsException("BCE1003");

		if (!this.userAccountService.checkPassword(password, user.getPassword()))
			throw new BadCredentialsException("BCE1004");

		return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	}

}
