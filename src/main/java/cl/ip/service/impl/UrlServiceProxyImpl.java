package cl.ip.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambdaworks.redis.api.sync.RedisCommands;

import cl.ip.response.UrlResponse;
import cl.ip.service.URLShortener;
import cl.ip.service.utils.RedisUtils;
import cl.ip.service.utils.UrlServiceProxy;

@Service
public class UrlServiceProxyImpl implements UrlServiceProxy{ 
	private static URLShortener urlShortener = new URLShortener();
	@Autowired
	RedisUtils redisUtils;

	public String saveToRedisShortnedUrl (String longUrl){
		RedisCommands<String, String> syncCommands = redisUtils.getRedisConnection().sync();
		String tinyURl ; 
		tinyURl = redisUtils.verifyExists(longUrl);
		if (tinyURl != null){
			return tinyURl;
		}
		tinyURl = getUrlShortener().shortenURL(longUrl);
		if (tinyURl.isEmpty()){
			return null;
		}
		syncCommands.set(tinyURl, longUrl);
		return tinyURl;

	}

	public String getExtendedUrlFromTinyUrl(String tinyUrl){
		RedisCommands<String, String> syncCommands = redisUtils.getRedisConnection().sync();
		String extendedUrl = syncCommands.get(tinyUrl);
		return extendedUrl;
	}
	public String deleteURL(String tinyUrl){
		RedisCommands<String, String> syncCommands = redisUtils.getRedisConnection().sync();
		String extendedUrlToDelete = syncCommands.get(tinyUrl);
		syncCommands.del(tinyUrl);
		return extendedUrlToDelete;
	}
	
	public URLShortener getUrlShortener(){
		if (urlShortener == null)
		{ 	urlShortener = new URLShortener();	
		}
		return urlShortener;
	}

	@Override
	public List<UrlResponse> findAll() {
		RedisCommands<String, String> syncCommands = redisUtils.getRedisConnection().sync();
		List<UrlResponse> urls = new ArrayList<>();
		for(String s : redisUtils.getallKeys(syncCommands)){
			urls.add(new UrlResponse(s));
		}
		return urls;
		 
	}

}
