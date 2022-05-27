package com.lucasbrandao.mycryptolist.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.lucasbrandao.mycryptolist.models.UserModel;
import com.lucasbrandao.mycryptolist.models.dto.UserDTO;

@Mapper(
	componentModel = "spring", 
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UsersMapper {
	
	UserModel toModel(UserDTO dto);
	
	UserDTO toDTO(UserModel model);
}
