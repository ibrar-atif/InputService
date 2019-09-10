package com.comtool.input.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	void readFile(String fileName) throws IOException;
	
	void uploadFile(MultipartFile[] files, boolean replaceFalg) throws Exception;
}
