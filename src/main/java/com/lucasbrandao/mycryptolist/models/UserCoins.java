package com.lucasbrandao.mycryptolist.models;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@RedisHash("Coins")
@Data
public class UserCoins {
	
	@Id
	@Indexed
	private UUID userUUID;
	
	@Indexed
	private String coinId;
	
	private Boolean isCurrentlyFavorite;
	
	private String userNotes;
	
	private Date createdAt;
	
	private Date updatedAt;
}
