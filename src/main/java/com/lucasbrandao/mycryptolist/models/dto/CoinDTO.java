package com.lucasbrandao.mycryptolist.models.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@Data
public class CoinDTO {
	
	private String id;

	private String name;

	private String symbol;

	private Integer rank;
	 
	private Boolean isNew;

	private Boolean isActive;

	private String type;
	
	private String description;

	private LocalDate lastUpdated;
}
