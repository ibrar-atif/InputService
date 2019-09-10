package com.comtool.input.service.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.comtool.input.dto.ProductDto;
import com.comtool.input.exception.FileServiceEx;
import com.comtool.input.service.CsvThreadReader;
import com.comtool.input.service.FileService;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class FileServiceImpl implements FileService{
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	KafkaTemplate< String, ProductDto> kafkaTemplate;
	
	@Value("${file.mount.path}")
	private String mountPath;
	
	@Value("${file.read.thread.count}")
	private Integer thread;
	
	@Value("${product.topic}")
	private String productTopic;

	@SuppressWarnings("unchecked")
	public void readFile(String fileName) throws IOException {
		
		FileReader csvFile = new FileReader(String.join(File.separator, mountPath,fileName));
		CSVReader csvReader = new CSVReader(csvFile);
		
		CsvToBean<ProductDto> csvToBean = new CsvToBeanBuilder<ProductDto>(csvReader).withType(ProductDto.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
		
		Iterator<ProductDto> csvIterator = csvToBean.iterator();
		for(int i=0;i<thread;i++) {
			CsvThreadReader productReader = context.getBean(CsvThreadReader.class);
			productReader.setIterator(csvIterator);
			productReader.setReader(csvReader);
			Thread t1 = new Thread(productReader,"Thread Read - "+i);
			t1.start();
		}
	}
	
	
	public void uploadFile(MultipartFile[] files,boolean replaceFalg) throws Exception {
		
		for (MultipartFile argMultipartFile : files) {
			String fileName = argMultipartFile.getOriginalFilename();
			File filepath = new File(mountPath);				
			if (!filepath.exists()) {
				FileUtils.forceMkdir(filepath);
			}				
			String fileLocation = String.join(File.separator, mountPath,fileName);
			File file = new File(fileLocation);
			if(file.exists() && !replaceFalg) {
				throw new FileServiceEx("File already Exist, if you want to replace send replace flag as true"); 
			}
			
			byte[] bytes = argMultipartFile.getBytes();
			Path path = Paths.get(fileLocation);
			
			Files.write(path, bytes);

		}
	}
	
}
