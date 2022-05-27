package com.lucasbrandao.mycryptolist.models.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@Data
public class UserLoginDTO {
	
	private UUID userUUID;
	
	private String username;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private Boolean isActive;
	
	private Date createdAt;

	private Date updatedAt;
}
