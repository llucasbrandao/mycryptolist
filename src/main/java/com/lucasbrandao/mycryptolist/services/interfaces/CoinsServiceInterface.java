package com.lucasbrandao.mycryptolist.services.interfaces;

import org.springframework.data.domain.Pageable;

import com.lucasbrandao.mycryptolist.models.dto.CoinDTO;
import com.lucasbrandao.mycryptolist.models.dto.PageDTO;

public interface CoinsServiceInterface {
	
	public PageDTO<CoinDTO> getCoins(Pageable pageable);
	
	public CoinDTO getCoinDetails(String coinId);
}
