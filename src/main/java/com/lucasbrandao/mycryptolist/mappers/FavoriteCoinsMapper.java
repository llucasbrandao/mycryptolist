package com.lucasbrandao.mycryptolist.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.lucasbrandao.mycryptolist.models.FavoriteCoinsModel;
import com.lucasbrandao.mycryptolist.models.dto.FavoriteCoinsDTO;

@Mapper(
	componentModel = "spring", 
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FavoriteCoinsMapper {
	
	FavoriteCoinsModel toModel(FavoriteCoinsDTO dto);
	
	FavoriteCoinsDTO toDTO(FavoriteCoinsModel model);
	
	List<FavoriteCoinsDTO> toDTOList(List<FavoriteCoinsModel> models);
}
