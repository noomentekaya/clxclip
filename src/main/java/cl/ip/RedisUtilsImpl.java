package cl.ip;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

@Service
public class RedisUtilsImpl implements RedisUtils{
	private RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
	private StatefulRedisConnection<String, String> connection = redisClient.connect();


	public StatefulRedisConnection<String, String> getRedisConnection (){
		if (connection == null){
			connection = redisClient.connect();
		}
		return connection;
	}

	public RedisClient getRedisClient() {
		if (redisClient == null ){
			redisClient =  RedisClient.create("redis://redisClient.connect();localhost:6379/0");
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
	private List<String> getallKeys(RedisCommands<String, String> syncCommands) {
		List<String> keys = syncCommands.keys("*");
		return keys;
	}
}
