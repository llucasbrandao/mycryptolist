package com.lucasbrandao.mycryptolist.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lucasbrandao.mycryptolist.models.UserLoginModel;

@Repository
public interface UsersLoginRepository extends CrudRepository<UserLoginModel, String> {
	
	Optional<UserLoginModel> findByUserUUID(UUID uuid);
	
	Optional<UserLoginModel> findByUsername(String username);
}
