package cl.ip.test;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

import cl.ip.RedisUtils;
import cl.ip.URLShortener;
import cl.ip.UrlServiceProxyImpl;
import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class UrlShortenerServiceTest {

	@Mock
	RedisUtils redisUtils;
	@Mock
	RedisCommands<String, String> syncCommands;
	@Mock
	StatefulRedisConnection<String, String> connection;
	@InjectMocks
	UrlServiceProxyImpl urlServiceProxy = new UrlServiceProxyImpl();

	String expandedUrl;
	String tinyUrl;
	URLShortener urlShortener;
	RedisClient redisClient;



	@Before
	public void setUp (){

		urlShortener = new URLShortener();
		redisClient = RedisClient.create("redis://localhost:6379/0");

	}

	@Test
	public void savetoRedisExistantExpandedUrlHttp(){
		expandedUrl = "http://www.opeth.com";
		tinyUrl = "cl.ip/sOMetIng5"	;
		when(redisUtils.getRedisClient()).thenReturn(redisClient);
		when(redisUtils.getRedisConnection()).thenReturn(redisClient.connect());
		when(redisUtils.verifyExists(expandedUrl)).thenReturn(tinyUrl);
		when(redisUtils.getRedisConnection()).thenReturn(connection);
		when(redisUtils.getRedisConnection().sync()).thenReturn(syncCommands);
		when(syncCommands.get(tinyUrl)).thenReturn(expandedUrl);
		when(urlServiceProxy.saveToRedisShortnedUrl(expandedUrl)).thenReturn(tinyUrl);
		Assert.assertEquals(expandedUrl, urlServiceProxy.getExtendedUrlFromTinyUrl(tinyUrl));;
	}

	@Test
	public void savetoRedisExistantExpandedUrlHttps(){
		expandedUrl = "https://www.opeth.com";
		tinyUrl = "cl.ip/sOMetIng5"	;
		when(redisUtils.getRedisClient()).thenReturn(redisClient);
		when(redisUtils.getRedisConnection()).thenReturn(redisClient.connect());
		when(redisUtils.verifyExists(expandedUrl)).thenReturn(tinyUrl);
		when(redisUtils.getRedisConnection()).thenReturn(connection);
		when(redisUtils.getRedisConnection().sync()).thenReturn(syncCommands);
		when(syncCommands.get(tinyUrl)).thenReturn(expandedUrl);
		when(urlServiceProxy.saveToRedisShortnedUrl(expandedUrl)).thenReturn(tinyUrl);
		Assert.assertEquals(expandedUrl, urlServiceProxy.getExtendedUrlFromTinyUrl(tinyUrl));;
	}

	@Test
	public void savetoRedisNonExistantExpandedUrlHttps(){
		expandedUrl = "https://www.opeth.com";
		tinyUrl = "cl.ip/sOMetIng5"	;
		when(redisUtils.getRedisClient()).thenReturn(redisClient);
		when(redisUtils.getRedisConnection()).thenReturn(redisClient.connect());
		when(redisUtils.verifyExists(expandedUrl)).thenReturn(null);
		when(redisUtils.getRedisConnection()).thenReturn(connection);
		when(redisUtils.getRedisConnection().sync()).thenReturn(syncCommands);
		when(syncCommands.get(tinyUrl)).thenReturn(expandedUrl);
		when(urlServiceProxy.saveToRedisShortnedUrl(expandedUrl)).thenReturn(tinyUrl);
		Assert.assertEquals(expandedUrl, urlServiceProxy.getExtendedUrlFromTinyUrl(tinyUrl));;
	}

	@Test
	public void savetoRedisNonExistantExpandedUrlHttp(){
		expandedUrl = "http://www.opeth.com";
		tinyUrl = "cl.ip/sOMetIng5"	;
		when(redisUtils.getRedisClient()).thenReturn(redisClient);
		when(redisUtils.getRedisConnection()).thenReturn(redisClient.connect());
		when(redisUtils.verifyExists(expandedUrl)).thenReturn(null);
		when(redisUtils.getRedisConnection()).thenReturn(connection);
		when(redisUtils.getRedisConnection().sync()).thenReturn(syncCommands);
		when(syncCommands.get(tinyUrl)).thenReturn(expandedUrl);
		when(urlServiceProxy.saveToRedisShortnedUrl(expandedUrl)).thenReturn(tinyUrl);
		Assert.assertEquals(expandedUrl, urlServiceProxy.getExtendedUrlFromTinyUrl(tinyUrl));;
	}
}