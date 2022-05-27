package com.lucasbrandao.mycryptolist.services;

import java.util.Date;
import java.util.UUID;

import javax.xml.ws.Holder;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lucasbrandao.mycryptolist.exceptions.DBException;
import com.lucasbrandao.mycryptolist.exceptions.NotFoundException;
import com.lucasbrandao.mycryptolist.mappers.UsersMapper;
import com.lucasbrandao.mycryptolist.models.dto.UserDTO;
import com.lucasbrandao.mycryptolist.repositories.UsersRepository;
import com.lucasbrandao.mycryptolist.services.interfaces.UsersServiceInterface;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersServiceInterface {
	
	private final UsersLoginServiceImpl usersLoginServiceImpl;
	
	private final UsersRepository usersRepository;
	
	private final UsersMapper usersMapper;
	
	@Override
	public void newUser(UserDTO dto) {
		if (dto.getName() != null 
				&& dto.getEmail() != null
				&& dto.getLoginDetails() != null) {
			
			dto.setId(UUID.randomUUID());
			dto.getLoginDetails().setUserUUID(dto.getId());
			dto.setAddedAt(new Date());
			
			try {
				usersRepository.save(usersMapper.toModel(dto));
				
			} catch (Exception e) {
				throw new DBException("Não foi possível salvar o usuário.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			usersLoginServiceImpl.newUserLogin(dto.getLoginDetails());
			
		} else {
			throw new IllegalArgumentException("Campos inválidos.");
		}
	}
	
	@Override
	public UserDTO getUser(UUID uuid) {
		Holder<UserDTO> dto = new Holder<>();
		
		usersRepository.findById(uuid).ifPresentOrElse(user -> {
			dto.value = usersMapper.toDTO(user);
			
		}, () -> {
			throw new NotFoundException("Nenhum usuário foi encontrado com o UUID " + uuid + ".");
		});
		
		return dto.value;
	}
	
	@Override
	public UserDTO login(String username, String password) {
		return getUser(usersLoginServiceImpl.loginUser(username, password).getUserUUID());
	}
}
