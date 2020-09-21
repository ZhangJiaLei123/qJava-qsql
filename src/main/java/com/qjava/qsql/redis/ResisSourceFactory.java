package com.qjava.qsql.redis;

import com.qjava.qsql.postgresql.DBPoolConnection;
import com.qjava.qsql.utils.RedisConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import java.io.File;
import java.util.List;

/**
 * @Author: Zhang.Jialei
 * @Date: 2020/9/12 16:43
 */
public class ResisSourceFactory {

    /** jedis连接池 */
    public static RedisPool redisPool;

    /** 主机ip */
    private static String redisIp = null;
    /** 端口 */
    private static Integer redisPort = null;
    /** 密码 */
    private static String pwd = null;
    /** 连接超时 */
    private static int redisTimeout = 3000;
    /** 默认数据库 */
    private static int block;

    /** 默认配置文件 */
    private static String filePath[] = new String[]{"resources/redis.properties", "redis.properties"};

    static {
        String webRootPath = null;
        // 从根开始计算相对路径
        webRootPath = DBPoolConnection.class.getClassLoader().getResource("").getPath();
        webRootPath = new File(webRootPath).getParent();
        // 依次找到默认配置文件
        for(String path : filePath){
            File file = new File(webRootPath + File.separator + path);
            if (file.exists()){
                newInstance(file.getParentFile());
                break;
            }
        }

    }

    public static synchronized boolean newInstance(File file){
        if (redisPool != null){
            return true;
        }
        RedisConfiguration properties = new RedisConfiguration(file);
        if (!properties.check()){
            System.out.println("redis连接池配置文件错误," +  file.getPath() + File.separator + properties.getFilename());
        }
        try {
            // Srouce工厂模式
            redisPool = createDataSource(properties);
        } catch (Exception e) {
            System.err.println("redis连接池配置文件错误," + file.getPath() + File.separator + properties.getFilename());
            e.printStackTrace();
            return false;
        }
        System.out.println("redis连接池配置成功");
        return true;
    }

    public static RedisPool createDataSource(RedisConfiguration properties){
        /** 连接地址 主机 */
        redisIp = properties.getHost();
        /** 端口 */
        redisPort = properties.getPort();
        /** 密码 */
        pwd = properties.getPassword();
        redisTimeout = properties.getRedis_timeout();
        /** 数据分区一般是0-16 */
        block = properties.getBlock();
        /** 默认key有效时间 */
        int expxTime = properties.getExpxtime();
        /** 最大连接数 */
        int maxTotal = properties.getMaxActive();
        int maxIdle = properties.getMaxIdle();
        int maxWait = properties.getMaxWait();
        int minIdle = properties.getMinIdle();
        boolean testOnBorrow = properties.getOnborrow();
        boolean testOnReturn = properties.getOnreturn();

        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        // 连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
        config.setBlockWhenExhausted(false);

        // 集群
//        JedisShardInfo jedisShardInfo1 = new JedisShardInfo(redisIp, redisPort);
//        jedisShardInfo1.setPassword(pwd);
//
//        List<JedisShardInfo> list = new LinkedList<JedisShardInfo>();
//        list.add(jedisShardInfo1);
//        pool = new ShardedJedisPool(config, list);

        return createDataSource(config, null);
    }

    public static RedisPool createDataSource(JedisPoolConfig config, List<JedisShardInfo> list){
        return new RedisPool(config, redisIp, redisPort, redisTimeout, pwd, block);
    }


    public static Jedis getJedis() {
        if(redisPool == null){
            return null;
        }
        return redisPool.getResource();
    }

    public static boolean returnBrokenResource(Jedis jedis) {
        if(redisPool == null){
            return false;
        }
        redisPool.returnBrokenResource(jedis);
        return true;
    }


    public static boolean returnResource(Jedis jedis) {
        if(redisPool == null){
            return false;
        }
        redisPool.returnResource(jedis);
        return true;
    }


}
