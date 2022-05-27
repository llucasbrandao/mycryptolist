package com.lucasbrandao.mycryptolist.clients.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "CoinPaprikaFeignClient", path = "${externalServices.coinPaprika.apiUrl}")
public interface CoinPaprikaFeignClient {

}
