package com.lucasbrandao.mycryptolist.models.dto;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@Data
public class UserCoinsDTO {
	
	private UUID userUUID;
	
	private String coinId;
	
	private Boolean isCurrentlyFavorite;
	
	private String userNotes;
	
	private Date addedAt;
	
	private Date lastUpdated;
}
