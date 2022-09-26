package com.github.theprogmatheus.zonadelivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO {

	private String name;
	private String email;
	private String phone;
	private String password;

}
