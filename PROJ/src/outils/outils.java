package outils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class outils{
	
	public static boolean isValidDate(String date) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false); 

	    try {
	        dateFormat.parse(date);
	        return true;
	    } catch (ParseException e) {
	        return false; 
	    }
	}

	public static boolean isValidTime(String time) {
	    Pattern pattern = Pattern.compile("^([01]\\d|2[0-3]):([0-5]\\d)$");
	    Matcher matcher = pattern.matcher(time);
	
	    return matcher.matches(); 
	}

}