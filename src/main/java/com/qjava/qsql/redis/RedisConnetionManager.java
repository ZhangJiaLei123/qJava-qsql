package com.qjava.qsql.redis;


import java.util.HashMap;
import java.util.Map;

/**
 * redis连接管理器
 *
 * @author 张家磊
 */
public class RedisConnetionManager {

   /**
     * 管理器单例
     */
    static RedisConnetionManager instance = null;
    /**
     * 基础连接属性
     */
    RedisBean redisBeanBase = null;
    /**
     * 连接管理池
     */
    Map<String, RedisConnetion> redisConnetionMap = new HashMap<>();

    /**
     * 创建单例,启动时调用
     * @param redisBeanBase
     * @return
     */
    public static RedisConnetionManager newInstance(RedisBean redisBeanBase) {

        if (instance == null) {
            instance = new RedisConnetionManager(redisBeanBase);
        }

        return instance;
    }

    /**
     * 获取单例
     * @return
     */
    public static RedisConnetionManager getInstance() {
        return instance;
    }

    /**
     * 内部构造方法
     * @param redisBeanBase
     */
    private RedisConnetionManager(RedisBean redisBeanBase) {
        this.redisBeanBase = redisBeanBase;
    }

    /**
     * 添加一个的表连接
     * @param database   连接器名称
     * @return
     */
    public RedisConnetion add(String database) {
        // 创建一个新的连接,如果连接已存在,就不创建了
        // 每一个用户,分别由一个连接进行管理
        RedisConnetion connetion = redisConnetionMap.get(database);
        if (connetion == null) {
            return add(database, redisBeanBase);
        }
        return connetion;
    }

    /**
     * 添加一个的表连接
     * @param redisBean   连接器名称
     * @param redisBean   连接器
     * @return
     */
    public RedisConnetion add(String database, RedisBean redisBean) {
        // 创建一个新的连接,如果连接已存在,就不创建了
        // 每一个用户,分别由一个连接进行管理
        RedisConnetion connetion = redisConnetionMap.get(database);
        if (connetion == null) {
            connetion = new RedisConnetion(redisBean);
            try {
                connetion.build();
            } catch (Exception e) {
                return null;
            }
            redisConnetionMap.put(database, connetion);
        }
        return connetion;
    }

    /**
     * 获取一个连接
     *
     * @param database 连接名称
     * @return
     */
    public RedisConnetion get(String database) {
        RedisConnetion connetion = redisConnetionMap.get(database);
        if (connetion == null) {
            connetion = add(database);
        }
        return connetion;
    }

}
