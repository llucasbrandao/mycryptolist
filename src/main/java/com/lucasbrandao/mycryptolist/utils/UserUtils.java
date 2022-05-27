package com.lucasbrandao.mycryptolist.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.lucasbrandao.mycryptolist.security.UserAuthentication;

public class UserUtils {
	
	public static UserAuthentication getLoggedUser() {
		return (UserAuthentication) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
