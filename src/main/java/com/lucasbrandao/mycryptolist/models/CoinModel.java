package com.lucasbrandao.mycryptolist.models;

import java.util.Date;

import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@RedisHash("Coins")
@Data
public class CoinModel {
	
	private String id;
	
	private String name;
	
	private String description;
	
	private Date createdAt;
	
	private Date updatedAt;
}
