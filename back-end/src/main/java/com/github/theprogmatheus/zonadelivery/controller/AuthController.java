package com.github.theprogmatheus.zonadelivery.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.theprogmatheus.zonadelivery.dto.AuthUserAccountDTO;
import com.github.theprogmatheus.zonadelivery.services.UserAccountService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserAccountService userAccountService;

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody AuthUserAccountDTO authUserAccountDTO) {
		try {
			if (authUserAccountDTO != null)
				return ResponseEntity.ok(userAccountService.register(authUserAccountDTO, new ArrayList<>()));
			else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("You need to provide the data required for registration");
		} catch (Exception exception) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
		}
	}

}
