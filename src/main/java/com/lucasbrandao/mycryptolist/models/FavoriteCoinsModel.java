package com.lucasbrandao.mycryptolist.models;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@RedisHash("FavoriteCoins")
@Data
public class FavoriteCoinsModel {
	
	@Indexed
	private UUID id;
	
	@Indexed
	private UUID userId;
	
	@Indexed
	private String coinId;
	
	private String userNotes;
	
	@Indexed
	private Boolean isStillFavorite;
	
	@Indexed
	private LocalDate createdAt;
	
	@Indexed
	private LocalDate updatedAt;
}
