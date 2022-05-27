package com.lucasbrandao.mycryptolist.utils;

import java.security.SecureRandom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashUtils {
	
	public static String bcryptHashing(String password, int workFactor) {
		BCryptPasswordEncoder bCryptPasswordEncoder =
				new BCryptPasswordEncoder(workFactor < 1 ? 10 : workFactor, new SecureRandom());
		 
		return bCryptPasswordEncoder.encode(password);
	}
}
