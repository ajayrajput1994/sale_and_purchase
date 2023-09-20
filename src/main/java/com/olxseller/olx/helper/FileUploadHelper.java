package com.olxseller.olx.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {

	//public final String UPLAOD_DIR="C:\\Users\\Ajay\\eclipse-workspace\\olx\\src\\main\\resources\\static\\image";
	public final String UPLAOD_DIR=new ClassPathResource("static/image").getFile().getAbsolutePath();
	
	public FileUploadHelper()throws IOException {
		
	}
	public boolean uploadfile(MultipartFile multipartfile,MultipartFile img) {
		boolean f=false;
		try {
			/*
			 * InputStream is=multipart.getInputStream(); byte data[]=new
			 * byte[is.available()]; is.read(data);
			 * 
			 * FileOutputStream fot=new
			 * FileOutputStream(UPLOAD_DIR+File.separator+multipart.getOriginalFilename());
			 * fot.write(data); fot.flush(); fot.close();
			 */
			
		Files.copy(multipartfile.getInputStream(),Paths.get(UPLAOD_DIR+File.separator+multipartfile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		Files.copy(img.getInputStream(),Paths.get(UPLAOD_DIR+File.separator+img.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		System.out.println("file path "+UPLAOD_DIR+File.separator+multipartfile.getOriginalFilename());
		f=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	
}
