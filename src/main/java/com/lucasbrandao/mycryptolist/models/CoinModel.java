package com.lucasbrandao.mycryptolist.models;

import java.time.LocalDate;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@RedisHash("Coins")
@Data
public class CoinModel {
	
	@Indexed
	private String id;
	
	@Indexed
	private String name;
	 
	@Indexed
	private String symbol;
	
	@Indexed
	private Integer rank;
	 
	private Boolean isNew;
	
	@Indexed
	private Boolean isActive;
	
	@Indexed
	private String type;
	
	private String description;
	
	@Indexed
	private LocalDate lastUpdated;
}
