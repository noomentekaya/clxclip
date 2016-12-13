package cl.ip.service.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import cl.ip.response.UrlResponse;
import cl.ip.service.URLShortener;

@Component
public interface UrlServiceProxy {
	public String saveToRedisShortnedUrl (String longUrl);
	public String getExtendedUrlFromTinyUrl(String tinyUrl);
	public String deleteURL(String tinyUrl);
	public URLShortener getUrlShortener();
	public List<UrlResponse> findAll();
}
