package com.olxseller.olx.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Configuration
public class Keys {

	@Value("${imageLocation}")
	private String imagepath;

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public static String getDate() {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date today = Calendar.getInstance().getTime();
		return df.format(today);
	}

	public String getYear() {
		String year = null;
		year = getDate().substring(getDate().lastIndexOf("/") + 1);
		return year;
	}

	public String getMonth() {
		String mon = getDate().substring(getDate().indexOf("-") + 1, getDate().lastIndexOf("-"));

		switch (mon) {
		case "01":
			return "Jan";

		case "02":
			return "Feb";

		case "03":
			return "Mar";

		case "04":
			return "Apr";

		case "05":
			return "May";

		case "06":
			return "June";

		case "07":
			return "July";

		case "08":
			return "Aug";

		case "09":
			return "Sep";

		case "10":
			return "Oct";

		case "11":
			return "Nov";

		case "12":
			return "Dec";

		default:
			return "00";
		}

	}

	public static String getTime() {
		DateFormat df = new SimpleDateFormat("hh:mm:ss");
		Date today = Calendar.getInstance().getTime();
		return df.format(today);
	}

	public String generateFilePath() {
		String path = getImagepath() + "//" + getDate() + "//";
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		return path;
	}

	// @Bean
	public String saveFile(MultipartFile[] files) throws IOException {
		StringJoiner fileNames=new StringJoiner(",");
		for(MultipartFile file : files){
			// Ensure the upload directory exists
			String uploadDir = generateFilePath();
			File uploadDirectory = new File(uploadDir);
			if (!uploadDirectory.exists()) {
					uploadDirectory.mkdirs();
			}
			// Generate a unique filename to avoid collisions
			String originalFilename = file.getOriginalFilename(); 
			String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

			// Save the file to the specified directory
			Path filePath = Paths.get(uploadDir, uniqueFilename); 
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			fileNames.add(getDate()+"/"+uniqueFilename);
		}
			// Return the unique filename for reference
			return fileNames.toString();
	}
		// @GetMapping("/images/{filename}")
		// @ResponseBody
		// public ResponseEntity<Resource> getImage(@PathVariable String filename) {
		// 		try {
		// 				Path file = Paths.get(uploadDir).resolve(filename);
		// 				Resource resource = new UrlResource(file.toUri());
		// 				if (resource.exists() || resource.isReadable()) {
		// 						return ResponseEntity.ok()
		// 										.contentType(MediaType.IMAGE_JPEG) // Adjust the media type if needed
		// 										.body(resource);
		// 				} else {
		// 						return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		// 				}
		// 		} catch (Exception e) {
		// 				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		// 		}
		// }

}
