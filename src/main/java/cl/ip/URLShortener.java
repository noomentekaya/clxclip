package cl.ip;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

public class URLShortener {
	@Autowired
	RedisUtils redisUtils;
	private String domain; // Use this attribute to generate urls for a custom
							// domain name defaults to http://fkt.in
	private char myChars[]; // This array is used for character to number
							// mapping
	private Random myRand; // Random object used to generate random integers
	private int keyLength; // the key length in URL defaults to 8

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

	// shortenURL
	// the public method which can be called to shorten a given URL
	public String shortenURL(String longURL) {
		String shortURL = "";
		if (validateURL(longURL)) {
			longURL = sanitizeURL(longURL);
			shortURL = domain + "/" + getKey(longURL);
		}
		// add http part
		return shortURL;
	}

	
	boolean validateURL(String url) {
		return true;
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
		boolean flag = true;
		while (flag) {
			key = "";
			for (int i = 0; i <= keyLength; i++) {
				key += myChars[myRand.nextInt(62)];
			}
		}
		return key;
	}
}