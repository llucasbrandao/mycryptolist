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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController()
@RequestMapping("api/users")	
public class GeneralController {
	
	private final UsersServiceImpl usersServiceImpl;
	
	@PostMapping("/newUser")
	public ResponseEntity<Void> newUser(@RequestBody UserDTO userDTO) {
		usersServiceImpl.newUser(userDTO);
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PostMapping("/teste")
	public ResponseEntity<Void> teste(@RequestBody UserDTO userDTO) {
		usersServiceImpl.newUser(userDTO);
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
