package com.lucasbrandao.mycryptolist.services.interfaces;

import java.util.UUID;

import com.lucasbrandao.mycryptolist.models.dto.UserDTO;

public interface UsersServiceInterface {
	
	public void newUser(UserDTO dto);
	
	public UserDTO getUser(UUID uuid);
	
	public UserDTO login(String username, String password);
}
