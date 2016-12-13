package cl.ip.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import cl.ip.service.utils.RedisUtils;

public class URLShortener {
	@Autowired
	RedisUtils redisUtils;
	private String domain; 
	private char myChars[];
	private Random myRand; 
	private int keyLength; 

	public URLShortener() {
		myRand = new Random();
		keyLength = 5;
		myChars = new char[62];
		for (int i = 0; i < 62; i++) {
			int j = 0;
			if (i < 10) {
				j = i + 48;
			} else if (i > 9 && i <= 35) {
				j = i + 55;
			} else {
				j = i + 61;
			}
			myChars[i] = (char) j;
		}
		domain = "http://cl.ip";
	}

	URLShortener(int length, String newDomain) {
		this();
		this.keyLength = length;
		if (!newDomain.isEmpty()) {
			newDomain = sanitizeURL(newDomain);
			domain = newDomain;
		}
	}

	public String shortenURL(String longURL) {
		String shortURL = "";
		if (validateURL(longURL)) {
			longURL = sanitizeURL(longURL);
			shortURL = domain + "/" + getKey(longURL);
		}
		return shortURL;
	}

	
//	boolean validateURL(String url) {
//		String[] schemes = {"http","https","www"};
//		UrlValidator urlValidator = new UrlValidator(schemes);
//		return urlValidator.isValid(url);
//
//		
//	}
	
	public boolean validateURL(String url) {
		if (url.substring(0, 7).equals("http://")){
			return true;}
		else if (url.substring(0, 8).equals("https://")){
			return true ;}
		else if (url.substring(0, 3).equals("www")){
			return true ;}
		
	return false;
	}

	
	String sanitizeURL(String url) {
			if (url.substring(0, 7).equals("http://"))
				url = url.substring(7);
			
			else if (url.substring(0, 8).equals("https://"))
				url = url.substring(8);

			else if (url.charAt(url.length() - 1) == '/')
				url = url.substring(0, url.length() - 1);
			
		return url;
	}

	
	private String getKey(String longURL) {
		String key;
		key = generateKey();
		return key;
	}

	private String generateKey() {
		String key = "";
			key = "";
			for (int i = 0; i <= keyLength; i++) {
				key += myChars[myRand.nextInt(62)];
			}
		return key;
	}
}
