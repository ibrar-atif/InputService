package com.comtool.input.service;

import java.io.IOException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.comtool.input.dto.ProductDto;
import com.opencsv.CSVReader;

@Component("csvReaderThread")
@Scope(value="prototype")
public class CsvThreadReader implements Runnable{

	@Autowired
	KafkaTemplate< String, ProductDto> kafkaTemplate;

	@Value("${product.topic}")
	private String productTopic;
	
	private volatile Iterator<ProductDto> iterator;
	
	private volatile CSVReader reader;
	
	private volatile boolean close;
	

	@Override
	public void run() {
			try {
				readFile();
			} catch (IOException e) {
				
				e.printStackTrace();
			}	
	}
	
	private void readFile() throws IOException {
		ProductDto productDto;
		while((productDto=getNextline()) != null) {
			kafkaTemplate.send(productTopic, productDto);
		}
				
		synchronized (iterator) {
			if(!close) {
				reader.close();
				close=true;
			}
		}
		
		
		
		
		
	}
	
	private ProductDto getNextline() throws IOException {
		synchronized (iterator) {
			if(!close && iterator.hasNext()) {
				return iterator.next();
			}			
			else
				return null;
		}
		
		
		
	}

	public Iterator<ProductDto> getIterator() {
		return iterator;
	}

	public void setIterator(Iterator<ProductDto> iterator) {
		this.iterator = iterator;
	}

	public CSVReader getReader() {
		return reader;
	}

	public void setReader(CSVReader reader) {
		this.reader = reader;
	}



}
