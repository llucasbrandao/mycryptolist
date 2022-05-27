package com.lucasbrandao.mycryptolist.models;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@RedisHash("CoinsSyncStatus")
@Data
public class CoinSyncStatusModel {
	
	@Indexed
	private UUID id;
	
	@Indexed
	public LocalDate lastSynced;
}
