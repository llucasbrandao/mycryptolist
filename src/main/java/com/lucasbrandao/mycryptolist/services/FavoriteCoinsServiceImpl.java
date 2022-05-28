package com.lucasbrandao.mycryptolist.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lucasbrandao.mycryptolist.exceptions.DBException;
import com.lucasbrandao.mycryptolist.exceptions.GenericException;
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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteCoinsServiceImpl implements FavoriteCoinsServiceInterface {
	
	private final CoinsServiceImpl coinsServiceImpl;
	
	private final FavoriteCoinsRepository favoriteCoinsRepository;
	
	private final FavoriteCoinsMapper favoriteCoinsMapper;
	
	public void markFavoriteCoin(FavoriteCoinsDTO favoriteCoinDTO) {
		if (favoriteCoinDTO != null && favoriteCoinDTO.getCoinId() != null) {
			favoriteCoinsRepository.findByUserIdAndCoinIdAndIsStillFavorite(UserUtils.getLoggedUser().getID(), 
					favoriteCoinDTO.getCoinId(), false).ifPresentOrElse(coin -> {
						if (!coin.getIsStillFavorite()) {
							coin.setUpdatedAt(LocalDate.now());
							coin.setIsStillFavorite(true);
							
							this.saveCoin(coin);
						}
						
					}, () -> {
						try {
							favoriteCoinDTO.setId(UUID.randomUUID());
							favoriteCoinDTO.setUserId(UserUtils.getLoggedUser().getID());
							favoriteCoinDTO.setCoinId(coinsServiceImpl.getCoinDetails(favoriteCoinDTO.getCoinId().toString()).getId());
							favoriteCoinDTO.setCreatedAt(LocalDate.now());
							favoriteCoinDTO.setIsStillFavorite(true);
							
							this.saveCoin(favoriteCoinsMapper.toModel(favoriteCoinDTO));
							
						} catch (NotFoundException e) {
							log.error(e.getLocalizedMessage());
							
							throw new GenericException(e.getLocalizedMessage(), HttpStatus.NOT_FOUND);
							
						} catch (Exception e) {
							log.error(e.getLocalizedMessage());
							
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
				
				this.saveCoin(coin);
				
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
	
	@Override
	public void updateFavoriteCoin(FavoriteCoinsDTO favoriteCoinsDTO) {
		if (favoriteCoinsDTO != null && favoriteCoinsDTO.getCoinId() != null) {
			favoriteCoinsRepository.findByUserIdAndCoinId(UserUtils.getLoggedUser().getID(), 
					favoriteCoinsDTO.getCoinId()).ifPresentOrElse(coin -> {
						
					coin.setUserNotes(favoriteCoinsDTO.getUserNotes());
					coin.setUpdatedAt(LocalDate.now());
					
					this.saveCoin(coin);
						
			}, () -> {
				throw new NotFoundException("Nenhuma moeda foi encontrada nos favoritos do usuário.");
			});
		}
	}
	
	private void saveCoin(FavoriteCoinsModel coin) {
		try {
			favoriteCoinsRepository.save(coin);
			
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			
			throw new DBException("Ocorreu um erro ao salvar os dados.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
