package com.lucasbrandao.mycryptolist.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@RedisHash("Users")
@Data
public class UserModel {
	
	private UUID id;
	
	@Indexed
	private String name;
	
	@Indexed
	private String email;
	
	private Date createdAt;
	
	private Date updatedAt;
}
