package com.comtool.input.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value=Include.NON_NULL)
public class ApiResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Object data;
	
	
	private ErrorDetailDto error;
	
	
	public ApiResponseDto() {
		
	}
	
	public ApiResponseDto(String id, Object value) {
		Map<String,Object> map = new HashMap<>();
		map.put(id, value);
		data = map;
		
	}
	
	public ApiResponseDto(ErrorDetailDto error) {
		this.error = error;
	}
	
	public ApiResponseDto(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ErrorDetailDto getError() {
		return error;
	}

	public void setError(ErrorDetailDto error) {
		this.error = error;
	}
	
	
}
