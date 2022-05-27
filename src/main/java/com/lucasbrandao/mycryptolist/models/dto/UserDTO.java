package com.lucasbrandao.mycryptolist.models.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@Data
public class UserDTO {
	
	private UUID id;
	
	private String name;
	
	private String email;
	
	private Date addedAt;
	
	private Date lastUpdated;
	
	private UserLoginDTO loginDetails;
}
