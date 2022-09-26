package com.github.theprogmatheus.zonadelivery.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.github.theprogmatheus.zonadelivery.model.UserAccountModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;
	private String username;
	private String email;
	private String phone;
	private String displayName;
	private List<String> authorities;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	public UserAccountDTO(final UserAccountModel userAccount) {
		if (userAccount != null) {
			this.id = userAccount.getId();
			this.username = userAccount.getName();
			this.email = userAccount.getEmail();
			this.phone = userAccount.getPhone();
			this.displayName = userAccount.getDisplayName();
			this.authorities = userAccount.getAuthorities().stream().map(authority -> authority.getAuthority())
					.collect(Collectors.toList());
			this.accountNonExpired = userAccount.isAccountNonExpired();
			this.accountNonLocked = userAccount.isAccountNonLocked();
			this.credentialsNonExpired = userAccount.isCredentialsNonExpired();
			this.enabled = userAccount.isEnabled();
		}
	}

}
