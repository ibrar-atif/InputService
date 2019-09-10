package com.comtool.input.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.comtool.input.dto.ApiResponseDto;
import com.comtool.input.dto.ErrorDetailDto;
import com.comtool.input.exception.FileServiceEx;

@RestControllerAdvice
public class InputServiceExAdvice {

	@ExceptionHandler(Throwable.class)
	public final ResponseEntity<ApiResponseDto> handleException(Throwable e) {
		ErrorDetailDto errorDetails = new ErrorDetailDto(e.getMessage(),"INPS1"); 
		return createResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(FileServiceEx.class)
	public final ResponseEntity<ApiResponseDto> handleBadDataException(FileServiceEx e) {
		ErrorDetailDto errorDetails = new ErrorDetailDto(e.getMessage(),"INPS1"); 
		return createResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	private ResponseEntity<ApiResponseDto> createResponseEntity(ErrorDetailDto errorDetails, HttpStatus httpStatus) {
		ApiResponseDto response = new ApiResponseDto(errorDetails);
		return new ResponseEntity<>(response, httpStatus);
	}
}
