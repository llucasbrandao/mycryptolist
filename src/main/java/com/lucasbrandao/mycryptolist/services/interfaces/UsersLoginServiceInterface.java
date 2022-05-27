package com.lucasbrandao.mycryptolist.services.interfaces;

import java.util.Optional;

import com.lucasbrandao.mycryptolist.models.dto.UserLoginDTO;

public interface UsersLoginServiceInterface {
	
	public void newUserLogin(UserLoginDTO userLoginDTO);
	
	public UserLoginDTO loginUser(String username, String password);
	
	public Optional<UserLoginDTO> loginUser(String username);
}
