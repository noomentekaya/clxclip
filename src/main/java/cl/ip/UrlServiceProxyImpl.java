package cl.ip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambdaworks.redis.api.sync.RedisCommands;

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

}
