package com.lucasbrandao.mycryptolist.services;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lucasbrandao.mycryptolist.security.UserAuthentication;


/*
 * Classe que implementa o contrato de autenticação do usuário padrão do Spring Security.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UsersLoginServiceImpl usersLoginServiceImpl;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Holder<UserDetails> userDetails = new Holder<>();
		
		// Usamos o email, ao invés do username
		usersLoginServiceImpl.loginUser(username).ifPresentOrElse(user -> {
			userDetails.value = new UserAuthentication(user.getUserUUID(), user.getUsername(), user.getPassword());
			
		}, () -> {
			throw new UsernameNotFoundException("");
		});
		
		return userDetails.value;
	}
}