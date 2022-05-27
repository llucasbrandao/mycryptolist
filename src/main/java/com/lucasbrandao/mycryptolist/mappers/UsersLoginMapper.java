package com.lucasbrandao.mycryptolist.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.lucasbrandao.mycryptolist.models.UserLoginModel;
import com.lucasbrandao.mycryptolist.models.dto.UserLoginDTO;

@Mapper(
	componentModel = "spring", 
	nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UsersLoginMapper {
	
	UserLoginModel toModel (UserLoginDTO dto);
	
	UserLoginDTO toDTO(UserLoginModel model);
}
