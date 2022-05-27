package com.lucasbrandao.mycryptolist.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@RedisHash("UsersLogin")
@Data
public class UserLoginModel {
	
	@Id
	@Indexed
	private UUID userUUID;
	
	@Indexed
	private String username;
	
	private String password;
	
	private Boolean isActive;
	
	private Date createdAt;

	private Date updatedAt;
}
