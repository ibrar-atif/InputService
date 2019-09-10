package com.comtool.input.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comtool.input.dto.ApiResponseDto;
import com.comtool.input.exception.FileServiceEx;
import com.comtool.input.service.FileService;
import com.comtool.input.utils.FileValidation;

@RestController
public class ReaderController {
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileValidation validation;

	@GetMapping("/read/{fileName}")
	public ResponseEntity<ApiResponseDto> readFiles(@PathVariable("fileName") String fileName) throws IOException, FileServiceEx {
		
		validation.fileExistValidation(fileName);
		fileService.readFile(fileName);
		return new ResponseEntity<>(new ApiResponseDto("message", "File read Successfully"),HttpStatus.OK);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<ApiResponseDto> uploadFile(
	@RequestParam(value = "files", required = false) MultipartFile[] files,
	@RequestParam(value = "replace", required = false, defaultValue="false") boolean replaceFalg) throws Exception{
		fileService.uploadFile(files, replaceFalg);
		return new ResponseEntity<>(new ApiResponseDto("message", "File Uploaded Successfully"),HttpStatus.OK);
	}
}
