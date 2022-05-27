package com.lucasbrandao.mycryptolist.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lucasbrandao.mycryptolist.models.UserModel;

@Repository
public interface UsersRepository extends CrudRepository<UserModel, UUID> {
	
	Page<UserModel> findAll(Pageable pageable);
	
	Optional<List<UserModel>> findByName(String name);
	
	Optional<UserModel> findByEmail(String email);
}
