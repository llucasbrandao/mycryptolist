package com.lucasbrandao.mycryptolist.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lucasbrandao.mycryptolist.models.CoinSyncStatusModel;

@Repository
public interface CoinsSyncStatusRepository extends CrudRepository<CoinSyncStatusModel, String> {
	
	Page<CoinSyncStatusModel> findAll(Pageable pageable);
}
