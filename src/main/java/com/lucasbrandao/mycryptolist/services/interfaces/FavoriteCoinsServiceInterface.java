package com.lucasbrandao.mycryptolist.services.interfaces;

import org.springframework.data.domain.Pageable;

import com.lucasbrandao.mycryptolist.models.dto.FavoriteCoinsDTO;
import com.lucasbrandao.mycryptolist.models.dto.PageDTO;

public interface FavoriteCoinsServiceInterface {
	
	public void markFavoriteCoin(FavoriteCoinsDTO favoriteCoinDTO);
	
	public void unmarkFavoriteCoin(FavoriteCoinsDTO favoriteCoinDTO);
	
	public PageDTO<FavoriteCoinsDTO> getFavoriteCoins(Pageable pageable);
	
	public void updateFavoriteCoin(FavoriteCoinsDTO favoriteCoinsDTO);
}
