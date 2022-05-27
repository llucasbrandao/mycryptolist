package com.lucasbrandao.mycryptolist.security;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserAuthentication implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String name;
	private String email;
	private String password;
	
	public UserAuthentication() {}
	
	public UserAuthentication(UUID id, String nome, String password) {
		super();
		this.id = id;
		this.name = nome;
		this.password = password;
	}
	
	public UserAuthentication(UUID id, String nome, String email, String password) {
		super();
		this.id = id;
		this.name = nome;
		this.email = email;
		this.password = password;
	}

	public UUID getID() {
		return id;
	}
	
	public String getNome() {
		return name;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		// Retorna o e-mail, ao invés do username
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
}

