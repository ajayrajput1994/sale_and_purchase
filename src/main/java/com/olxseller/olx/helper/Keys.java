package com.olxseller.olx.helper;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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

}
