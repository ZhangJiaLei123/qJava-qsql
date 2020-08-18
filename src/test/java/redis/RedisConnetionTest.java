package redis;

import com.qjava.qsql.redis.RedisConnetion;
import org.junit.jupiter.api.Test;

public class RedisConnetionTest {

    RedisConnetion redisConnetion = new RedisConnetion("sql.zhangjialei.cn", "redis", 6379, 0,
            -100, 100, 0, 0, 0);

    @Test
    public void send() {
        boolean fal = redisConnetion.build();

        if (!fal) {
            System.out.println("redis连接不成功");
        }
        redisConnetion.put("dev123", "" +  System.currentTimeMillis());

        fal = redisConnetion.exit("dev123");
        if (!fal) {
            System.out.println("key不存在");
            return;
        }

        String res = redisConnetion.get("dev123");
        System.out.println("key:" + res);
    }
}
