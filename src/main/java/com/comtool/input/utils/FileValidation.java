package com.comtool.input.utils;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.comtool.input.exception.FileServiceEx;

@Component
public class FileValidation {
	
	@Value("${file.mount.path}")
	private String mountPath;
	
	public boolean fileExistValidation(String fileName) throws FileServiceEx {
		String fileLocation = String.join(File.separator, mountPath,fileName);
		File file = new File(fileLocation);
		if(!file.exists()) {
			throw new FileServiceEx("No file exist with name "+fileName);
		}
		return true;
	}

}
