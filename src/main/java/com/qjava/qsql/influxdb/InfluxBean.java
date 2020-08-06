package com.qjava.qsql.influxdb;

/**
 * 一个influxdb的连接参数
 * @auth 张家磊
 */
public class InfluxBean {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 连接地址 http://192.168.0.118:8086
     */
    private String openurl;
    /**
     * 数据库名
     */
    private String database;
    /**
     * 表名
     */
    private String tablename;
    /**
     * 保留策略名
     */
    private String retentionPolicy;

    /**
     * 构造方法
     * @param username          用户名
     * @param password          密码
     * @param openurl           连接地址 http://192.168.0.118:8086
     * @param database          数据库名
     * @param retentionPolicy   保留策略名
     */
    public InfluxBean(String username, String password, String openurl, String database, String retentionPolicy) {
        this.username = username;
        this.password = password;
        this.openurl = openurl;
        this.database = database;
        this.retentionPolicy = retentionPolicy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenurl() {
        return openurl;
    }

    public void setOpenurl(String openurl) {
        this.openurl = openurl;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getRetentionPolicy() {
        return retentionPolicy;
    }

    public void setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
}
