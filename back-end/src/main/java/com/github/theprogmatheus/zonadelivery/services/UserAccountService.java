package com.github.theprogmatheus.zonadelivery.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.theprogmatheus.zonadelivery.dto.UserAccountDTO;
import com.github.theprogmatheus.zonadelivery.model.UserAccountModel;
import com.github.theprogmatheus.zonadelivery.repository.UserAccountRepository;

@Service
public class UserAccountService {

	private static PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Autowired
	private UserAccountRepository userAccountRepository;

	public UserAccountModel register(UserAccountDTO userAccountDTO, List<String> authorities) throws Exception {
		try {
			return this.userAccountRepository.saveAndFlush(
					new UserAccountModel(null, userAccountDTO.getName().toLowerCase(), userAccountDTO.getEmail(),
							userAccountDTO.getPhone(), passwordEncoder.encode(userAccountDTO.getPassword()),
							userAccountDTO.getName(), authorities, true, true, true, true));
		} catch (Exception exception) {
			throw exception;
		}
	}

	public UserAccountModel changePassword(UUID id, String newPassword) throws Exception {
		try {
			Optional<UserAccountModel> userAccountOptional = this.userAccountRepository.findById(id);

			if (userAccountOptional.isEmpty())
				throw new AccountNotFoundException("The account with the id '" + id + "' does not exist");

			UserAccountModel userAccountModel = userAccountOptional.get();

			userAccountModel.setPassword(passwordEncoder.encode(newPassword));

			return this.userAccountRepository.saveAndFlush(userAccountModel);
		} catch (Exception exception) {
			throw exception;
		}
	}

	public UserAccountModel changeEmail(UUID id, String newEmail) throws Exception {
		try {
			Optional<UserAccountModel> userAccountOptional = this.userAccountRepository.findById(id);

			if (userAccountOptional.isEmpty())
				throw new AccountNotFoundException("The account with the id '" + id + "' does not exist");

			if (this.userAccountRepository.findByEmail(newEmail) != null)
				throw new Exception("A user with this e-mail already exists, choose another one");

			UserAccountModel userAccountModel = userAccountOptional.get();

			// validate e-mail before

			userAccountModel.setEmail(newEmail);

			return this.userAccountRepository.saveAndFlush(userAccountModel);
		} catch (Exception exception) {
			throw exception;
		}
	}

	public UserAccountModel changePhone(UUID id, String newPhone) throws Exception {
		try {
			Optional<UserAccountModel> userAccountOptional = this.userAccountRepository.findById(id);

			if (userAccountOptional.isEmpty())
				throw new AccountNotFoundException("The account with the id '" + id + "' does not exist");

			if (this.userAccountRepository.findByName(newPhone) != null)
				throw new Exception("A user with this phone already exists, choose another one");

			UserAccountModel userAccountModel = userAccountOptional.get();

			// validate phone before

			userAccountModel.setPhone(newPhone);

			return this.userAccountRepository.saveAndFlush(userAccountModel);
		} catch (Exception exception) {
			throw exception;
		}
	}

	public UserAccountModel changeName(UUID id, String newName) throws Exception {
		try {

			// set username to lowercase
			newName = newName.toLowerCase();

			Optional<UserAccountModel> userAccountOptional = this.userAccountRepository.findById(id);

			if (userAccountOptional.isEmpty())
				throw new AccountNotFoundException("The account with the id '" + id + "' does not exist");

			if (this.userAccountRepository.findByName(newName) != null)
				throw new Exception("A user with this username already exists, choose another one");

			UserAccountModel userAccountModel = userAccountOptional.get();

			// validate phone before

			userAccountModel.setPhone(newName);

			return this.userAccountRepository.saveAndFlush(userAccountModel);
		} catch (Exception exception) {
			throw exception;
		}
	}

	public UserAccountModel changeDisplayName(UUID id, String displayName) throws Exception {
		try {

			Optional<UserAccountModel> userAccountOptional = this.userAccountRepository.findById(id);

			if (userAccountOptional.isEmpty())
				throw new AccountNotFoundException("The account with the id '" + id + "' does not exist");

			UserAccountModel userAccountModel = userAccountOptional.get();
			userAccountModel.setDisplayName(displayName);

			return this.userAccountRepository.saveAndFlush(userAccountModel);
		} catch (Exception exception) {
			throw exception;
		}
	}

	public boolean checkPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}

	public UserAccountModel findById(UUID id) {
		System.out.println(id);
		for (UserAccountModel u : this.userAccountRepository.findAll()) {
			System.out.println(u.getId());
		}

		Optional<UserAccountModel> a = this.userAccountRepository.findById(id);

		System.out.println(a.isPresent());
		return this.userAccountRepository.findById(id).orElse(null);
	}

	public UserAccountModel findByEmail(String email) {
		return this.userAccountRepository.findByEmail(email).orElse(null);
	}

	public UserAccountModel findByName(String name) {
		return this.userAccountRepository.findByName(name).orElse(null);
	}

	public UserAccountModel findByPhone(String phone) {
		return this.userAccountRepository.findByPhone(phone).orElse(null);
	}

	public UserAccountRepository getUserAccountRepository() {
		return userAccountRepository;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

}
