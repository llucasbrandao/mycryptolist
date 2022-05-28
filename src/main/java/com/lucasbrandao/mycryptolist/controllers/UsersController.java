package com.lucasbrandao.mycryptolist.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasbrandao.mycryptolist.models.dto.UserDTO;
import com.lucasbrandao.mycryptolist.services.UsersServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController()
@RequestMapping("api/users")	
@Tag(name = "Users Controller")
public class UsersController {
	
	private final UsersServiceImpl usersServiceImpl;
	
	@Operation(
			summary = "Cadastra um novo usuário.", 
			description = "É necessário informar apenas o name, email, username e password."
	)
	@PostMapping("/newUser")
	public ResponseEntity<Void> newUser(@RequestBody UserDTO userDTO) {
		usersServiceImpl.newUser(userDTO);
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
