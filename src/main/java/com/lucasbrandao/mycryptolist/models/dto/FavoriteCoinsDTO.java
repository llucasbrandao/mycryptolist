package com.lucasbrandao.mycryptolist.models.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@Data
public class FavoriteCoinsDTO {
	
	private UUID id;
	
	private UUID userId;
	
	private String coinId;
	
	private String userNotes;
	
	private Boolean isStillFavorite;
	
	private LocalDate createdAt;
	
	private LocalDate updatedAt;
}