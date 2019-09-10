package com.comtool.input.test.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import com.comtool.input.service.FileService;
import com.comtool.input.utils.FileValidation;

import scala.collection.mutable.AnyRefMap.AnyRefMapBuilder;



@RunWith(SpringRunner.class)
@WebMvcTest
public class ReaderControllerTest {
	
	@MockBean
	private FileService fileService;
	
	@MockBean
	private FileValidation validation;
	
	@Autowired
	private MockMvc mockMvc;
		
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void readTest() throws Exception {
		
		doNothing().when(fileService).readFile(anyString());
		when(validation.fileExistValidation(anyString())).thenReturn(true);
		
		MvcResult response = mockMvc.perform(get("/read/dataExample.csv")).andReturn();
		
		Assert.assertEquals(200, response.getResponse().getStatus());


	}
	
	@Test
	public void uploadTest() throws Exception {
		
		byte[] b = {};		
		MvcResult response = mockMvc.perform(fileUpload("/upload").file("data",b)).andReturn();
		
		Assert.assertEquals(200, response.getResponse().getStatus());


	}
	
	
	
}
