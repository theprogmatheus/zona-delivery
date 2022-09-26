package com.github.theprogmatheus.zonadelivery.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserAccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String email;
	private String phone;
	private String displayName;

}
