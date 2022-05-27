package com.lucasbrandao.mycryptolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
@EnableFeignClients
public class MyCriptoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCriptoListApplication.class, args);
	}
}
