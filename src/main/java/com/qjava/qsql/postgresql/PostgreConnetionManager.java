package com.qjava.qsql.postgresql;


import java.util.HashMap;
import java.util.Map;

/**
 * Postgre连接器管理
 * @Author: Zhang.Jialei
 * @Date: 2020/8/3 11:59
 */
public class PostgreConnetionManager {
    static PostgreConnetionManager instance = null;
    PostgreBean postgreBean = null;
    /**
    /**
     * 连接管理池
     */
    Map<String, PostgreConnetionBase> redisConnetionMap = new HashMap<>();

    public static PostgreConnetionManager getInstance(){
        return instance;
    }

    public static PostgreConnetionManager newInstance(PostgreBean postgreBean){

        if(instance == null){
            instance = new PostgreConnetionManager(postgreBean);
        }

        return instance;
    }

    private PostgreConnetionManager(PostgreBean postgreBean){
        this.postgreBean = postgreBean;
    }


    /**
     * 添加一个的表连接
     * @param database   连接器名称
     * @return
     */
    public PostgreConnetionBase add(String database) {
        PostgreConnetionBase connetion = redisConnetionMap.get(database);
        if (connetion == null) {
            return add(database, postgreBean);
        }
        return connetion;
    }

    /**
     * 添加一个的表连接
     * @param database   连接器名称
     * @return
     */
    public PostgreConnetionBase add(String database, PostgreBean postgreBean) {
        // 创建一个新的连接,如果连接已存在,就不创建了
        // 每一个用户,分别由一个连接进行管理
        PostgreConnetionBase connetion = redisConnetionMap.get(database);
        if (connetion == null) {
            connetion = new PostgreConnetionBase(postgreBean);
            try {
                connetion.connect();
            } catch (Exception e) {
                e.printStackTrace();
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
    public PostgreConnetionBase get(String database) {
        PostgreConnetionBase connetion = redisConnetionMap.get(database);
        if (connetion == null) {
            connetion = add(database);
        }
        return connetion;
    }

}
