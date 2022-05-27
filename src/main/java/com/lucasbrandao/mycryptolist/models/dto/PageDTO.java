package com.lucasbrandao.mycryptolist.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageDTO<T> {
	
	private List<T> content;
	
    private Long totalElements;

    private int totalPages;

    private int pageNumber;

    private int requestSize;
}
