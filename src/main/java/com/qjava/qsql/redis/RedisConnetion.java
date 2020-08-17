package com.qjava.qsql.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

/**
 * Redis连接器
 *
 * @author MI
 */
public class RedisConnetion {

    Jedis jedis = null;

    /**
     * 连接主机192.168.0.118
     */
    private String host;
    private String pwd;
    private int port;
    private int database = 0;
    private int maxActive = -1;
    private int maxIdle = 100;
    private int maxWait = -11;
    private int minIdle = 0;
    private int redisTimeout = 0;
    /**
     * 超时时间, 秒
     */
    private int expxTime = 3;

    public RedisConnetion(RedisBean redisBeanBase) {
        this.host = redisBeanBase.getHost();
        this.pwd = redisBeanBase.getPwd();
        this.port = redisBeanBase.getPort();
        this.database = redisBeanBase.getDatabase();
        this.maxActive = redisBeanBase.getMaxActive();
        this.maxIdle = redisBeanBase.getMaxIdle();
        this.maxWait = redisBeanBase.getMaxWait();
        this.minIdle = redisBeanBase.getMinIdle();
        this.redisTimeout = redisBeanBase.getRedisTimeout();
        this.expxTime = redisBeanBase.getExpxTime();
    }

    public RedisConnetion(String host, String pwd, int port, int database,
                          int maxActive, int maxIdle, int maxWait, int minIdle, int redisTimeout) {
        this.host = host;
        this.pwd = pwd;
        this.port = port;
        this.database = database;
        this.maxActive = maxActive;
        this.maxIdle = maxIdle;
        this.maxWait = maxWait;
        this.minIdle = minIdle;
        this.redisTimeout = redisTimeout;
    }


    /**
     * 连接
     *
     * @return
     */
    public boolean build() {
        try {
            jedis = new Jedis(host, port);
        } catch (Exception e) {
            return false;
        }
        if (pwd != null) {
            jedis.auth(pwd);
        }
        return true;
    }

    /**
     * 连接
     */
    public void connect(){
        jedis.connect();
        if (pwd != null) {
            jedis.auth(pwd);
        }
    }


    /**
     * 判断连接
     * @return
     */
    public boolean isConnected(){
        return jedis.isConnected();
    }

    /**
     * 添加key,同时设置默认超时时间
     * <p>
     * 默认使用nx模式,不存在时才set
     *
     * @param key   key值
     * @param value value值
     * @return
     */
    public boolean put(String key, String value) {

        try {
            jedis.set(key, value, new SetParams().nx().ex(expxTime));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 添加key,同时设置默认超时时间
     * <p>
     * 使用xx模式,存在时才set
     *
     * @param key   key值
     * @param value value值
     * @return
     */
    public boolean putxx(String key, String value) {

        try {
            jedis.set(key, value, new SetParams().xx().ex(expxTime));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 添加key,list
     *
     * @param key
     * @param strings 1   value
     *                2   NX是不存在时才set， XX是存在时才set，
     *                3   EX是秒，PX是毫秒
     *                4   过期时间，单位是expx所代表的单位。
     * @return
     */
    public boolean put(String key, String... strings) {
        if (jedis == null) {
            return false;
        }
        jedis.lpush(key, strings);
        return true;
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return jedis.get(key);
    }


    /**
     * 删除key
     * @param key
     * @return
     */
    public boolean remove(String... key){
        if (jedis == null) {
            return false;
        }
        if(jedis.del(key) > 0){
            return true;
        };


        return false;
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public boolean remove(String key){
        if (jedis == null) {
            return false;
        }
        if(jedis.del(key) > 0){
            return true;
        };

        return false;
    }
    /**
     * 判断存在
     *
     * @param key
     * @return
     */
    public boolean exit(String key) {
        return jedis.exists(key);
    }

    public int getExpxTime() {
        return expxTime;
    }

    public void setExpxTime(int expxTime) {
        this.expxTime = expxTime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getRedisTimeout() {
        return redisTimeout;
    }

    public void setRedisTimeout(int redisTimeout) {
        this.redisTimeout = redisTimeout;
    }

    @Override
    public String toString() {
        return "RedisConnetion{" +
                "host='" + host + '\'' +
                ", pwd='" + pwd + '\'' +
                ", port=" + port +
                ", database=" + database +
                ", maxActive=" + maxActive +
                ", maxIdle=" + maxIdle +
                ", maxWait=" + maxWait +
                ", minIdle=" + minIdle +
                ", redisTimeout=" + redisTimeout +
                ", expxTime=" + expxTime +
                '}';
    }
}
