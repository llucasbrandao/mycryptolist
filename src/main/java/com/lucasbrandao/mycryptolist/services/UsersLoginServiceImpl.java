package com.lucasbrandao.mycryptolist.services;

import java.util.Optional;

import javax.xml.ws.Holder;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lucasbrandao.mycryptolist.exceptions.DBException;
import com.lucasbrandao.mycryptolist.exceptions.DuplicateItemException;
import com.lucasbrandao.mycryptolist.exceptions.GenericException;
import com.lucasbrandao.mycryptolist.mappers.UsersLoginMapper;
import com.lucasbrandao.mycryptolist.models.dto.UserLoginDTO;
import com.lucasbrandao.mycryptolist.repositories.UsersLoginRepository;
import com.lucasbrandao.mycryptolist.services.interfaces.UsersLoginServiceInterface;
import com.lucasbrandao.mycryptolist.utils.HashUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersLoginServiceImpl implements UsersLoginServiceInterface {
	
	private final UsersLoginRepository usersLoginRepository;
	
	private final UsersLoginMapper usersLoginMapper;
	
	@Override
	public void newUserLogin(UserLoginDTO userLoginDTO) {
		if (userLoginDTO != null 
				&& userLoginDTO.getUserUUID() != null
				&& userLoginDTO.getUsername() != null
				&& userLoginDTO.getPassword() != null) {
			
			usersLoginRepository.findByUsername(userLoginDTO.getUsername()).ifPresent(__ -> {
				throw new DuplicateItemException("Username já existe.");
			});
			
			usersLoginRepository.findByUserUUID(userLoginDTO.getUserUUID()).ifPresent(__ -> {
				throw new DuplicateItemException("Já existe um acesso para o usuário.");
			});
				
			try {
				userLoginDTO.setIsActive(true);
				userLoginDTO.setPassword(HashUtils.bcryptHashing(userLoginDTO.getPassword(), 10));
				
				usersLoginRepository.save(usersLoginMapper.toModel(userLoginDTO));
				
			} catch (Exception e) {
				throw new DBException("Não foi possível salvar o usuário.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		} else {
			throw new IllegalArgumentException("Dados inválidos.");
		}
	}
	
	@Override
	public UserLoginDTO loginUser(String username, String password) {
		if (username != null && password != null) {
			Holder<UserLoginDTO> dto = new Holder<UserLoginDTO>();
			
			usersLoginRepository.findByUsername(username).ifPresentOrElse(user -> {
				if (!password.equals(user.getPassword())) {
					throw new GenericException("Usuário e/ou senha inválido(s).", HttpStatus.FORBIDDEN);
				}
				
				dto.value = usersLoginMapper.toDTO(user);
				
			}, () -> {
				throw new GenericException("Usuário e/ou senha inválido(s).", HttpStatus.FORBIDDEN);
			});
			
			return dto.value;
		}
		
		throw new IllegalArgumentException("Usuário e/ou senha inválido(s)");
	}
	
	@Override
	public Optional<UserLoginDTO> loginUser(String username) {
		if (username != null) {
			Holder<UserLoginDTO> dto = new Holder<UserLoginDTO>();
			
			usersLoginRepository.findByUsername(username).ifPresentOrElse(user -> {
				dto.value = usersLoginMapper.toDTO(user);
				
			}, () -> {
				throw new GenericException("Usuário e/ou senha inválido(s).", HttpStatus.FORBIDDEN);
			});
			
			return Optional.of(dto.value);
		}
		
		throw new IllegalArgumentException("Usuário e/ou senha inválido(s)");
	}
}
