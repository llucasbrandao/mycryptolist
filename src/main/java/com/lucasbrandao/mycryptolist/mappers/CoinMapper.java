package com.lucasbrandao.mycryptolist.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.lucasbrandao.mycryptolist.models.CoinModel;
import com.lucasbrandao.mycryptolist.models.dto.CoinDTO;

@Mapper(
	componentModel = "spring", 
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CoinMapper {
	
	CoinDTO toDTO(CoinModel model);
	
	CoinModel toModel(CoinDTO dto);
	
	List<CoinDTO> toDTOList(List<CoinModel> modelList);
}
