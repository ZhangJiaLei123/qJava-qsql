package com.qjava.qsql.postgresql;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.qjava.qsql.utils.Toos;

import java.io.File;
import java.sql.SQLException;
import java.util.Properties;

/**
 * druid连接池
 * @Author: Zhang.Jialei
 * @Date: 2020/9/10 9:45
 */
public class DBPoolConnection {

    private static DBPoolConnection dbPoolConnection = null;
    private static DruidDataSource druidDataSource = null;

    /** 默认配置文件 */
    private static String filePath[] = new String[]{"resources/db_server.properties",
            "db_server.properties"};

    static {
        String webRootPath = null;
        // 从根开始计算相对路径
        webRootPath = DBPoolConnection.class.getClassLoader().getResource("").getPath();
        webRootPath = new File(webRootPath).getParent();
        // 依次找到默认配置文件
        for(String path : filePath){
            File file = new File(webRootPath + File.separator + path);
            if (file.exists()){
                newInstance(file);
                break;
            }
        }
    }

    public static synchronized boolean newInstance(File file){
        if (druidDataSource != null){
            return true;
        }
        Properties properties = Toos.loadPropertiesFile(file);
        try {
            // DruidDataSrouce工厂模式
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            System.err.println("druid连接池配置文件错误:" + file.getPath());
            e.printStackTrace();
            return false;
        }
        System.out.println("druid连接池配置成功");
        return true;
    }

    /**
     * 数据库连接池单例
     * @return
     */
    public static synchronized DBPoolConnection getInstance(){
        if (null == dbPoolConnection){
            dbPoolConnection = new DBPoolConnection();
        }
        return dbPoolConnection;
    }

    /**
     * 返回druid数据库连接
     * @return
     * @throws SQLException
     */
    public synchronized DruidPooledConnection getConnection() throws SQLException{
        return druidDataSource.getConnection();
    }


}