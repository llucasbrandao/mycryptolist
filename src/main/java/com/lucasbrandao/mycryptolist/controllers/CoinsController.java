package com.lucasbrandao.mycryptolist.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucasbrandao.mycryptolist.models.dto.CoinDTO;
import com.lucasbrandao.mycryptolist.models.dto.FavoriteCoinsDTO;
import com.lucasbrandao.mycryptolist.models.dto.PageDTO;
import com.lucasbrandao.mycryptolist.services.CoinsServiceImpl;
import com.lucasbrandao.mycryptolist.services.FavoriteCoinsServiceImpl;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController()
@RequestMapping("api/coins")	
@Tag(name = "Coins Controller")
@OpenAPIDefinition(info = @Info(title = "My Crypto List API", 
	description = "API de informações sobre criptomoedas. Todas as chamadas são autenticadas via JWT.", version = "1.0"))
public class CoinsController {
	
	private final CoinsServiceImpl coinsServiceImpl;
	
	private final FavoriteCoinsServiceImpl favoriteCoinsServiceImpl;
	
	@GetMapping("/")
	public ResponseEntity<PageDTO<CoinDTO>> getCoins(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer size) {
		
		return new ResponseEntity<PageDTO<CoinDTO>>(coinsServiceImpl.getCoins(PageRequest.of(page - 1, size)), HttpStatus.OK);
	}
	
	@GetMapping("/details")
	public ResponseEntity<CoinDTO> getCoinDetails(@RequestParam String coinId) {
		return new ResponseEntity<CoinDTO>(coinsServiceImpl.getCoinDetails(coinId), HttpStatus.OK);
	}
	
	@PostMapping("/favorite/add")
	public ResponseEntity<Void> markFavoriteCoin(@RequestBody FavoriteCoinsDTO favoriteCoinsDTO) {
		favoriteCoinsServiceImpl.markFavoriteCoin(favoriteCoinsDTO);
		
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/favorite/remove")
	public ResponseEntity<Void> unmarkFavoriteCoin(@RequestBody FavoriteCoinsDTO favoriteCoinsDTO) {
		favoriteCoinsServiceImpl.unmarkFavoriteCoin(favoriteCoinsDTO);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping("/favorite")
	public ResponseEntity<PageDTO<FavoriteCoinsDTO>> getFavoriteCoins(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue = "10") Integer size) {
		
		return new ResponseEntity<PageDTO<FavoriteCoinsDTO>>(favoriteCoinsServiceImpl.getFavoriteCoins(PageRequest.of(page - 1, size)), HttpStatus.OK);
	}
	
	@PatchMapping("/favorite/update")
	public ResponseEntity<Void> updateFavoriteCoin(@RequestBody FavoriteCoinsDTO favoriteCoinsDTO) {
		favoriteCoinsServiceImpl.updateFavoriteCoin(favoriteCoinsDTO);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
