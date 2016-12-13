package cl.ip.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

import cl.ip.service.utils.RedisUtils;

@Service
public class RedisUtilsImpl implements RedisUtils{
	private RedisClient redisClient;
	private StatefulRedisConnection<String, String> connection;
	@Value("${redis.host}")
    private String host;
	@Value("${redis.port}")
    private String port;  
	@Value("${redis.cluster}")
	private String cluster;

	public StatefulRedisConnection<String, String> getRedisConnection (){
		if (connection == null){
			connection = getRedisClient().connect();
		}
		return connection;
	}

	public RedisClient getRedisClient() {
		if (redisClient == null ){
			redisClient =  RedisClient.create(getRedisUrl());
		}
		return redisClient;
	}
	public void closeConnectionToRedis(StatefulRedisConnection<String, String> connection,RedisClient client) {
		connection.close();
		client.shutdown();
	}

	@Override
	public String verifyExists(String url) {
		RedisCommands<String, String> syncCommands = getRedisConnection().sync();
		List<String> keys = getallKeys(syncCommands);
		for (String key : keys){
			if (url.equals(syncCommands.get(key))){
				return key;
			}
		}
		
		return null;
	}
	
	@Cacheable
	public  List<String> getallKeys(RedisCommands<String, String> syncCommands) {
		List<String> keys = syncCommands.keys("*");
		return keys;
	}
	private String getRedisUrl(){
		return "redis://"+host+":"+port+"/"+cluster;
	}
}
