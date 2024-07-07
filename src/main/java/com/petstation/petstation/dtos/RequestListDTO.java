package com.petstation.petstation.dtos;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class RequestListDTO {
	
	private int page;
	private int rows;
	private Map<String, String> filters;
	private String sortField;
	private int sortOrder;

}
