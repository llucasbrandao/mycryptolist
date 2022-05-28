package com.lucasbrandao.mycryptolist.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.xml.ws.Holder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lucasbrandao.mycryptolist.clients.feign.CoinPaprikaFeignClient;
import com.lucasbrandao.mycryptolist.exceptions.GenericException;
import com.lucasbrandao.mycryptolist.exceptions.NotFoundException;
import com.lucasbrandao.mycryptolist.mappers.CoinMapper;
import com.lucasbrandao.mycryptolist.models.CoinModel;
import com.lucasbrandao.mycryptolist.models.CoinSyncStatusModel;
import com.lucasbrandao.mycryptolist.models.dto.CoinDTO;
import com.lucasbrandao.mycryptolist.models.dto.PageDTO;
import com.lucasbrandao.mycryptolist.repositories.CoinsRepository;
import com.lucasbrandao.mycryptolist.repositories.CoinsSyncStatusRepository;
import com.lucasbrandao.mycryptolist.services.interfaces.CoinsServiceInterface;
import com.lucasbrandao.mycryptolist.utils.PageableUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoinsServiceImpl implements CoinsServiceInterface {
	
	private final CoinPaprikaFeignClient coinPaprikaFeignClient;
	
	private final CoinsRepository coinsRepository;
	
	private final CoinsSyncStatusRepository coinsSyncStatusRepository;
	
	private final CoinMapper coinMapper;
	
	private static Integer CACHE_DAYS = 10;
	
	@Override
	public PageDTO<CoinDTO> getCoins(Pageable pageable) {
		Holder<Page<CoinModel>> page = new Holder<>();
		
		if (isDateBeforeNow(getLastSyncedCache())) {
			Holder<PageDTO<CoinDTO>> dtos = new Holder<>();
			
			coinPaprikaFeignClient.getCoins().ifPresentOrElse(coins -> {
				if (coins.size() == 0) {
					throw new GenericException("Nenhuma moeda foi encontrada.", HttpStatus.EXPECTATION_FAILED);
				}
				
				this.addCoinsToCache(coins);
				
				page.value = coinsRepository.findAll(pageable);
				
				dtos.value = PageableUtils.generatePageDTO(coinMapper.toDTOList(page.value.getContent()), page.value.getTotalElements(), 
						page.value.getTotalPages(), page.value.getNumberOfElements(), page.value.getNumber());

			}, () -> {
				throw new NotFoundException("Nenhuma moeda encontrada.");
			});
			
			return dtos.value;
			
		} else {
			page.value = coinsRepository.findAll(pageable);
			
			return PageableUtils.generatePageDTO(coinMapper.toDTOList(page.value.getContent()), page.value.getTotalElements(), 
					page.value.getTotalPages(), page.value.getNumberOfElements(), page.value.getNumber());
		}
	}
	
	@Override
	public CoinDTO getCoinDetails(String coinId) {
		Holder<CoinDTO> dto = new Holder<>();
		
		coinsRepository.findById(coinId).ifPresentOrElse(coin -> {
			if (coin.getDescription() == null || isDateBeforeNow(coin.getLastUpdated())) {
				dto.value = coinMapper.toDTO(updateCoinDescription(coin));
				
			} else {
				dto.value = coinMapper.toDTO(coin);
			}
			
		}, () -> {
			coinPaprikaFeignClient.getCoinDetails(coinId).ifPresentOrElse(coin -> {
				if (coin == null) {
					throw new NotFoundException("Nenhuma moeda foi encontrada com o ID " + coinId + ".");
				}
				
				this.addCoinToCache(coin);
				
				dto.value = coin;
				
			}, () -> {
				throw new NotFoundException("Nenhuma moeda foi encontrada com o ID " + coinId + ".");
			});
		});
		
		return dto.value;
	}
	
	private void addCoinsToCache(List<CoinDTO> coins) {
		if (coins != null && coins.size() > 0) {
			if (isDateBeforeNow(getLastSyncedCache())) {
				coins.parallelStream().forEach(coin -> {
					coin.setLastUpdated(LocalDate.now());
					
					coinsRepository.save(coinMapper.toModel(coin));
				});
				
				CoinSyncStatusModel coinSyncStatusModel = new CoinSyncStatusModel();
				
				coinSyncStatusModel.setId(UUID.randomUUID());
				coinSyncStatusModel.setLastSynced(LocalDate.now());
				
				coinsSyncStatusRepository.save(coinSyncStatusModel);
			}
		}
	}
	
	private void addCoinToCache(CoinDTO coin) {
		if (coin != null) {
			coin.setLastUpdated(LocalDate.now());
				
			coinsRepository.save(coinMapper.toModel(coin));
			
			return;
		}
		
		throw new IllegalArgumentException("Dados inválidos.");
	}
	
	private CoinModel updateCoinDescription(CoinModel coin) {
		coinPaprikaFeignClient.getCoinDetails(coin.getId()).ifPresent(details -> {
			coin.setDescription(details.getDescription());
			coin.setLastUpdated(LocalDate.now());
			
		});
		
		return coinsRepository.save(coin);
	}
	
	private LocalDate getLastSyncedCache() {
		Page<CoinSyncStatusModel> page = coinsSyncStatusRepository.findAll(PageRequest.of(0, 1, Sort.by("lastSynced").descending()));
		Holder<LocalDate> lastDate = new Holder<>();
		
		page.getContent().forEach(status -> {
			lastDate.value = status.getLastSynced();
		});
		
		return lastDate.value;
	}
	
	private boolean isDateBeforeNow(LocalDate date) {
		return date == null || date.plusDays(CACHE_DAYS).isBefore(LocalDate.now());
	}
}
