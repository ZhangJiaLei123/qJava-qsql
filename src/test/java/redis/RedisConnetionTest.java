package redis;

import com.qjava.qsql.redis.RedisPoolUtil;
import org.junit.jupiter.api.Test;

public class RedisConnetionTest {

//    RedisConnetion redisConnetion = new RedisConnetion("auth.zhangjialei.cn", "redis", 8379, 0,
//            -100, 100, 0, 0, 0);

    @Test
    public void send() {
        boolean fal = false;

        System.out.println("hello:" + RedisPoolUtil.get("hello2"));
        System.out.println("hello:" + RedisPoolUtil.setEx("hello2", "hello", 3));
        System.out.println("hello:" + RedisPoolUtil.get("hello2"));


    }
}
