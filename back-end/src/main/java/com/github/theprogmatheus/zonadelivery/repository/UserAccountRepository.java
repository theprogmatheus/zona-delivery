package com.github.theprogmatheus.zonadelivery.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.theprogmatheus.zonadelivery.model.UserAccountModel;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccountModel, UUID> {

	public abstract Optional<UserAccountModel> findByName(String name);

	public abstract Optional<UserAccountModel> findByEmail(String email);

	public abstract Optional<UserAccountModel> findByPhone(String phone);

}
