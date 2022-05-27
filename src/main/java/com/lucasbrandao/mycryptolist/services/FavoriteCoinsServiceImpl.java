package com.lucasbrandao.mycryptolist.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lucasbrandao.mycryptolist.exceptions.DBException;
import com.lucasbrandao.mycryptolist.exceptions.NotFoundException;
import com.lucasbrandao.mycryptolist.mappers.FavoriteCoinsMapper;
import com.lucasbrandao.mycryptolist.models.FavoriteCoinsModel;
import com.lucasbrandao.mycryptolist.models.dto.FavoriteCoinsDTO;
import com.lucasbrandao.mycryptolist.models.dto.PageDTO;
import com.lucasbrandao.mycryptolist.repositories.FavoriteCoinsRepository;
import com.lucasbrandao.mycryptolist.services.interfaces.FavoriteCoinsServiceInterface;
import com.lucasbrandao.mycryptolist.utils.PageableUtils;
import com.lucasbrandao.mycryptolist.utils.UserUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteCoinsServiceImpl implements FavoriteCoinsServiceInterface {
	
	private final CoinsServiceImpl coinsServiceImpl;
	
	private final FavoriteCoinsRepository favoriteCoinsRepository;
	
	private final FavoriteCoinsMapper favoriteCoinsMapper;
	
	public void markFavoriteCoin(FavoriteCoinsDTO favoriteCoinDTO) {
		if (favoriteCoinDTO != null && favoriteCoinDTO.getCoinId() != null) {
			favoriteCoinsRepository.findByUserIdAndCoinIdAndIsStillFavorite(UserUtils.getLoggedUser().getID(), 
					favoriteCoinDTO.getCoinId(), true).ifPresentOrElse(coin -> {
						if (!coin.getIsStillFavorite()) {
							coin.setUpdatedAt(LocalDate.now());
							coin.setIsStillFavorite(true);
							
							favoriteCoinsRepository.save(coin);
						}
						
					}, () -> {
						try {
							favoriteCoinDTO.setId(UUID.randomUUID());
							favoriteCoinDTO.setUserId(UserUtils.getLoggedUser().getID());
							favoriteCoinDTO.setCoinId(coinsServiceImpl.getCoinDetails(favoriteCoinDTO.getCoinId().toString()).getId());
							favoriteCoinDTO.setCreatedAt(LocalDate.now());
							
							favoriteCoinsRepository.save(favoriteCoinsMapper.toModel(favoriteCoinDTO));
							
						} catch (Exception e) {
							throw new DBException("Ocorreu um erro ao salvar os dados.", HttpStatus.INTERNAL_SERVER_ERROR);
						}
					});
			
			return;
		}
		
		throw new IllegalArgumentException("Dados inválidos.");
	}
	
	@Override
	public void unmarkFavoriteCoin(FavoriteCoinsDTO favoriteCoinDTO) {
		if (favoriteCoinDTO != null && favoriteCoinDTO.getCoinId() != null) {
			favoriteCoinsRepository.findByUserIdAndCoinIdAndIsStillFavorite(UserUtils.getLoggedUser().getID(), 
					favoriteCoinDTO.getCoinId(), true).ifPresentOrElse(coin -> {
						
				coin.setIsStillFavorite(false);
				coin.setUpdatedAt(LocalDate.now());
				
				try {
					favoriteCoinsRepository.save(coin);
					
				} catch (Exception e) {
					throw new DBException("Ocorreu um erro ao salvar os dados.", HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
			}, () -> {
				throw new NotFoundException("Nenhuma moeda com o ID " + favoriteCoinDTO.getCoinId() + " foi encontrada nos favoritos do usuário.");
			});
			
			return;
		}
		
		throw new IllegalArgumentException("Dados inválidos.");
	}
	
	@Override
	public PageDTO<FavoriteCoinsDTO> getFavoriteCoins(Pageable pageable) {
		Page<FavoriteCoinsModel> page = favoriteCoinsRepository.findByUserIdAndIsStillFavorite(UserUtils.getLoggedUser().getID(), true, pageable);
		
		return PageableUtils.generatePageDTO(favoriteCoinsMapper.toDTOList(page.getContent()), page.getTotalElements(), 
				page.getTotalPages(), page.getNumberOfElements(), page.getNumber());
	}
}
