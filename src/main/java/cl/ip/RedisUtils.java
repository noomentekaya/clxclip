package cl.ip;

import java.util.List;

import org.springframework.stereotype.Component;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;

@Component
public interface RedisUtils {
	public StatefulRedisConnection<String, String> getRedisConnection ();
	public RedisClient getRedisClient();
	public void closeConnectionToRedis(StatefulRedisConnection<String, String> connection,RedisClient client);
	public String verifyExists(String url);
	public  List<String> getallKeys(RedisCommands<String, String> syncCommands);
}
