package com.qjava.qsql.influxdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * influx数据库连接管理器
 * @author MI
 */
public class InfluxConnectionManager {
    static InfluxConnectionManager instance = null;
 /**
     * 连接池
     * String            用户名
     * InfluxConnection  连接对象
     **/
    Map<String, InfluxConnection> influxMaps = new HashMap<>();

    /** 基础数据库连接信息 **/
    InfluxBean influxBaseBean = null;

    /**
     * 单例模式
     * @param username
     * @param password
     * @param openurl
     * @param database
     * @param retentionPolicy
     * @return
     */
    @Deprecated
    public static InfluxConnectionManager newInstance(String username, String password, String openurl,
                                                                                                   String database, String retentionPolicy) {
        if(instance == null){
            instance = new InfluxConnectionManager(username, password, openurl, database, retentionPolicy);
        }
        return instance;
    }

    public static InfluxConnectionManager newInstance(InfluxBean influxBean) {
        if(instance == null){
            instance = new InfluxConnectionManager(influxBean);
        }
        return instance;
    }

    /**
     * 获取influx连接管理器
     * @return
     */
    public static InfluxConnectionManager getInstance(){
        return instance;
    }


    /**
     * 内部构造方法
     * @param username          用户名
     * @param password          密码
     * @param openurl           连接地址 http://192.168.0.118:8086
     * @param database          数据库名
     * @param retentionPolicy   保留策略名
     */
    private InfluxConnectionManager(String username, String password, String openurl,
                                   String database, String retentionPolicy) {
        influxBaseBean = new InfluxBean(username, password, openurl, database, retentionPolicy, false);
    }

    private InfluxConnectionManager(InfluxBean influxBean) {
        influxBaseBean = influxBean;
    }

    /**
     * 添加一个的表连接
     * @param tableName
     * @return
     */
    public InfluxConnection add(String tableName){
        // 创建一个新的连接,如果连接已存在,就不创建了
        // 每一个用户,分别由一个连接进行管理
        InfluxConnection influxConnection = influxMaps.get(tableName) ;
        if (influxConnection == null){
            influxConnection = add(tableName, influxBaseBean);
        }

        return influxConnection;
    }

    /**
     * 添加一个的表连接
     * @param tableName
     * @return
     */
    public InfluxConnection add(String tableName, InfluxBean influxBean){
        // 创建一个新的连接,如果连接已存在,就不创建了
        // 每一个用户,分别由一个连接进行管理
        InfluxConnection influxConnection = influxMaps.get(tableName) ;
        if (influxConnection == null){
            influxBean.setDatabase(tableName);
            influxConnection = new InfluxConnection(influxBean);
            influxMaps.put(tableName, influxConnection);
        }

        return influxConnection;
    }

    /**
     * 获取连接对象
     * @param name
     * @return
     */
    public InfluxConnection getInfluxConnection(String name){
        return influxMaps.get(name);
    }


    /**
     * 获取连接对象
     * @param name
     * @param creat  不存在是否创建
     * @return
     */
    public InfluxConnection getInfluxConnection(String name, boolean creat){
        InfluxConnection connection = influxMaps.get(name);

        if(connection == null){
            InfluxBean influxBean = influxBaseBean;
            influxBean.setCreatDateBase(creat);
            this.add(name, influxBean);
        }
        return influxMaps.get(name);
    }



}
