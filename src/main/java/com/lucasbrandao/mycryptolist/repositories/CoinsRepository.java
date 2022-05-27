package com.lucasbrandao.mycryptolist.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lucasbrandao.mycryptolist.models.CoinModel;

@Repository
public interface CoinsRepository extends CrudRepository<CoinModel, String> {

}
