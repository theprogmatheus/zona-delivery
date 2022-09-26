package com.github.theprogmatheus.zonadelivery.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.theprogmatheus.zonadelivery.ZonaDeliveryApplication;
import com.github.theprogmatheus.zonadelivery.dto.AuthUserAccountDTO;
import com.github.theprogmatheus.zonadelivery.dto.OccurrenceResponseDTO;
import com.github.theprogmatheus.zonadelivery.dto.UserAccountDTO;
import com.github.theprogmatheus.zonadelivery.model.UserAccountModel;
import com.github.theprogmatheus.zonadelivery.services.UserAccountService;
import com.github.theprogmatheus.zonadelivery.util.ProcessedServiceResult;
import com.github.theprogmatheus.zonadelivery.util.ProcessedServiceResult.ProcessedServiceResultType;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserAccountService userAccountService;

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody AuthUserAccountDTO authUserAccountDTO) {
		try {

			ProcessedServiceResult<UserAccountModel> processedServiceResult = this.userAccountService
					.register(authUserAccountDTO, new ArrayList<>());

			if (processedServiceResult.getStatus() == ProcessedServiceResultType.SUCCESS)
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new UserAccountDTO(processedServiceResult.getResult()));
			else
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OccurrenceResponseDTO(
						processedServiceResult.getStatus().name(), processedServiceResult.getMessage()));

		} catch (Exception exception) {

			ZonaDeliveryApplication.log
					.error("Ocorreu um erro ao tentar registrar um novo usuário: " + exception.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OccurrenceResponseDTO("ERROR",
					"Ocorreu um erro interno ao tentar registrar um novo usuário, entre em contato com o suporte."));
		}
	}

}
