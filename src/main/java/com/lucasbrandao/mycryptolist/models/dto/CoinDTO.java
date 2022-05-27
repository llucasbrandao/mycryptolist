package com.lucasbrandao.mycryptolist.models.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(Include.NON_EMPTY)
@Data
public class CoinDTO {
	
	private String id;
	
	private String name;
	
	private String description;
	
	private Date createdAt;
	
	private Date updatedAt;
}
