package com.comtool.input.test.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.multipart.MultipartFile;

import com.comtool.input.exception.FileServiceEx;
import com.comtool.input.service.impl.FileServiceImpl;

@RunWith(PowerMockRunner.class)		
@PrepareForTest({FileServiceImpl.class,MultipartFile.class,File.class,Paths.class,Files.class})
public class FileServiceImplTest {
	
	@InjectMocks
	private FileServiceImpl fileService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	

	@Test(expected=FileServiceEx.class)
	public void uploadTestEx() throws Exception {
		MultipartFile[] m = {PowerMockito.mock(MultipartFile.class)};
		when(m[0].getOriginalFilename()).thenReturn("dataExample.csv");
		File file = mock(File.class);   
	    PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);
	    when(file.exists()).thenReturn(true).thenReturn(true); 
		
	    fileService.uploadFile(m, false);
	    
		
	}
	
	@Test
	public void uploadTest() throws Exception {
		MultipartFile[] m = {PowerMockito.mock(MultipartFile.class)};
		when(m[0].getOriginalFilename()).thenReturn("dataExample.csv");
		File file = mock(File.class);   
	    PowerMockito.whenNew(File.class).withAnyArguments().thenReturn(file);
	    when(file.exists()).thenReturn(true).thenReturn(false);
	    PowerMockito.mockStatic(Paths.class);
	    Path path = mock(Path.class);
	    when(Paths.get(anyString())).thenReturn(path);
	    PowerMockito.mockStatic(Files.class);
	    PowerMockito.when(Files.write(any(Path.class), any(byte[].class))).thenReturn(path);
		fileService.uploadFile(m, false);
		
		verify(file,atLeastOnce()).exists();

	}
	

}
