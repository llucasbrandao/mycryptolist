package com.lucasbrandao.mycryptolist.clients.feign;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.lucasbrandao.mycryptolist.models.dto.CoinDTO;

@FeignClient(name = "CoinPaprikaFeignClient", url = "${externalServices.coinPaprika.apiUrl}")
public interface CoinPaprikaFeignClient {
	
	@GetMapping("/coins")
	public Optional<List<CoinDTO>> getCoins();
	
	@GetMapping("/coins/{coinId}")
	public Optional<CoinDTO> getCoinDetails(@PathVariable String coinId);
}
