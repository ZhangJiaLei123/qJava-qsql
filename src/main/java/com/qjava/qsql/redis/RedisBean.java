package com.qjava.qsql.redis;

/**
 * redis连接属性
 * @author 张家磊
 */
public class RedisBean {
    /** 连接地址 主机192.168.0.118 */
    private String host;
    /** 密码 */
    private String pwd;
    /** 端口 */
    private int port;
    /** 数据分区一般是0-16 */
    private int database = 0;
    /** 默认key有效时间 , 秒 */
    private int expxTime = 3;
    /** 连接池最大连接数, 负值认为没有限制  */
    private int maxActive = -1;
    /** 连接池中的最大空闲连接 */
    private int maxIdle =   100;
    /** 连接池最大阻塞等待时间（使用负值表示没有限制） */
    private int maxWait =   -11;
    /** 连接池中的最小空闲连接 */
    private int minIdle =   0;
    /** 连接超时时间（毫秒） */
    private int redisTimeout = 0;

    /**
     * 构造方法
     * @param host         主机
     * @param port         端口
     * @param pwd          密码
     * @param database     区块
     * @param expxTime     key有效时间
     * @param maxActive    连接池最大连接数, 负值认为没有限制
     * @param maxIdle      连接池中的最大空闲连接
     * @param maxWait      连接池最大阻塞等待时间（使用负值表示没有限制）
     * @param minIdle      连接池中的最小空闲连接
     * @param redisTimeout 连接超时时间（毫秒）
     */
    public RedisBean(String host, int port, String pwd, int database, int expxTime,
                     int maxActive, int maxIdle, int maxWait, int minIdle, int redisTimeout) {
        this.host = host;
        this.port = port;
        this.pwd = pwd;
        this.database = database;
        this.expxTime = expxTime;
        this.maxActive = maxActive;
        this.maxIdle = maxIdle;
        this.maxWait = maxWait;
        this.minIdle = minIdle;
        this.redisTimeout = redisTimeout;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPwd() {
        return pwd;
    }

    public int getDatabase() {
        return database;
    }

    public int getExpxTime() {
        return expxTime;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public int getRedisTimeout() {
        return redisTimeout;
    }


    public RedisBean setHost(String host) {
        this.host = host;
        return this;
    }

    public RedisBean setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public RedisBean setPort(int port) {
        this.port = port;
        return this;
    }

    public RedisBean setDatabase(int database) {
        this.database = database;
        return this;
    }

    public RedisBean setMaxActive(int maxActive) {
        this.maxActive = maxActive;
        return this;
    }

    public RedisBean setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public RedisBean setMaxWait(int maxWait) {
        this.maxWait = maxWait;
        return this;
    }

    public RedisBean setMinIdle(int minIdle) {
        this.minIdle = minIdle;
        return this;
    }

    public RedisBean setExpxTime(int expxTime) {
        this.expxTime = expxTime;
        return this;
    }

    public RedisBean setRedisTimeout(int redisTimeout) {
        this.redisTimeout = redisTimeout;
        return this;
    }
}
