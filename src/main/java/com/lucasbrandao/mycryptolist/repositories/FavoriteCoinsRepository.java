package com.lucasbrandao.mycryptolist.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lucasbrandao.mycryptolist.models.FavoriteCoinsModel;

@Repository
public interface FavoriteCoinsRepository extends CrudRepository<FavoriteCoinsModel, UUID> {
	
	Optional<FavoriteCoinsModel> findByUserIdAndCoinIdAndIsStillFavorite(UUID userId, String coinId, Boolean isStillFavorite);
	
	Optional<FavoriteCoinsModel> findByUserIdAndCoinId(UUID userId, String coinId);
	
	Page<FavoriteCoinsModel> findByUserIdAndIsStillFavorite(UUID userId, Boolean isStillFavorite, Pageable pageable);
}
