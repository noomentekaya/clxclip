package cl.ip;

import org.springframework.stereotype.Component;

@Component
public interface UrlServiceProxy {
	public String saveToRedisShortnedUrl (String longUrl);
	public String getExtendedUrlFromTinyUrl(String tinyUrl);
	public String deleteURL(String tinyUrl);
	public URLShortener getUrlShortener();
}
