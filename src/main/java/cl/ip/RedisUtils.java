package cl.ip;

import org.springframework.stereotype.Component;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;

@Component
public interface RedisUtils {
	public StatefulRedisConnection<String, String> getRedisConnection ();
	public RedisClient getRedisClient();
	public void closeConnectionToRedis(StatefulRedisConnection<String, String> connection,RedisClient client);
	public String verifyExists(String url);

}
