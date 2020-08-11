package com.qjava.qsql.influxdb;


import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.*;
import org.influxdb.dto.Point.Builder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * InfluxDB数据库连接操作类
 *
 * @author Mi
 */
public class InfluxConnection {
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
     * 不存在是否创建
     */
    private boolean isCreatDateBase = false;

    private InfluxDB influxDB;

    /**
     * @param username        用户名
     * @param password        密码
     * @param openurl         连接地址
     * @param database        数据库 , 数据库名,不得有异常字符,如'/'等,最好只有英文和数字
     * @param retentionPolicy 保留策略
     */
    public InfluxConnection(String username, String password, String openurl, String database,
                            String retentionPolicy) {
        this.username = username;
        this.password = password;
        this.openurl = openurl;
        this.database = database;
        this.retentionPolicy = retentionPolicy == null || retentionPolicy.equals("") ? "defaule" : retentionPolicy;
        influxDbBuild();
    }

    public InfluxConnection(InfluxBean influxBean) {
        this(influxBean.getUsername(), influxBean.getPassword(), influxBean.getOpenurl(),
                influxBean.getDatabase(), influxBean.getRetentionPolicy());
    }

    /**
     * 创建数据库
     *
     * @param dbName
     */
    @SuppressWarnings("deprecation")
    public void createDB(String dbName) {
        influxDB.createDatabase(dbName);
    }

    /**
     * 删除数据库
     *
     * @param dbName
     */
    @SuppressWarnings("deprecation")
    public void deleteDB(String dbName) {
        influxDB.deleteDatabase(dbName);
    }

    /**
     * 测试连接是否正常
     *
     * @return true 正常
     */
    public boolean ping() {
        boolean isConnected = false;
        Pong pong;
        try {
            pong = influxDB.ping();
            if (pong != null) {
                isConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    /**
     * 连接时序数据库 ，若不存在则创建
     *
     * @return
     */
    public InfluxDB influxDbBuild() {
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(openurl, username, password);
        }
        if (influxDB == null) {
            return null;
        }
        try {
            if (isCreatDateBase && !influxDB.databaseExists(database)) {
                createDB(database);
                createDefaultRetentionPolicy("0h");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            influxDB.setRetentionPolicy(retentionPolicy);
        }
        influxDB.setLogLevel(InfluxDB.LogLevel.NONE);
        return influxDB;
    }

    /**
     * 创建自定义保留策略
     *
     * @param policyName  策略名
     * @param duration    保存天数
     * @param replication 保存副本数量
     * @param isDefault   是否设为默认保留策略
     */
    public void createRetentionPolicy(String policyName, String duration, int replication, Boolean isDefault) {
        String sql = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s ", policyName,
                database, duration, replication);
        if (isDefault) {
            sql = sql + " DEFAULT";
        }
        this.query(sql);
    }

    /**
     * 创建默认的保留策略
     * default，保存天数：30天，保存副本数量：1
     * 设为默认保留策略
     * time为0h时,认为是永久保存
     *
     * @param times 保存时长, xxD :周w, 天d,小时h,分钟m,秒s
     */
    public void createDefaultRetentionPolicy(String times) {
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "default", database, times, 1);
        this.query(command);
    }

    /**
     * 查询
     *
     * @param command 查询语句
     * @return
     */
    public QueryResult query(String command) {
        return influxDB.query(new Query(command, database));
    }

    /**
     * 插入
     *
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     */
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields, long time,
                       TimeUnit timeUnit) {
        Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);
        if (0 != time) {
            builder.time(time, timeUnit);
        }
        influxDB.write(database, retentionPolicy, builder.build());
    }

    /**
     * 批量写入测点
     *
     * @param batchPoints
     */
    public void batchInsert(BatchPoints batchPoints) {
        influxDB.write(batchPoints);
        // influxDB.enableGzip();
        // influxDB.enableBatch(2000,100,TimeUnit.MILLISECONDS);
        // influxDB.disableGzip();
        // influxDB.disableBatch();
    }

    /**
     * 批量写入数据
     *
     * @param database        数据库
     * @param retentionPolicy 保存策略
     * @param consistency     一致性
     * @param records         要保存的数据（调用BatchPoints.lineProtocol()可得到一条record）
     */
    public void batchInsert(final String database, final String retentionPolicy, final ConsistencyLevel consistency,
                            final List<String> records) {
        influxDB.write(database, retentionPolicy, consistency, records);
    }

    /**
     * 删除
     *
     * @param command 删除语句
     * @return 返回错误信息
     */
    public String deleteMeasurementData(String command) {
        QueryResult result = influxDB.query(new Query(command, database));
        return result.getError();
    }

    /**
     * 关闭数据库
     */
    public void close() {
        influxDB.close();
    }

    /**
     * 构建Point
     *
     * @param measurement
     * @param time
     * @param fields
     * @return
     */
    public Point pointBuilder(String measurement, long time, Map<String, String> tags, Map<String, Object> fields) {
        Point point = Point.measurement(measurement).time(time, TimeUnit.MILLISECONDS).tag(tags).fields(fields).build();
        return point;
    }

    public boolean isCreatDateBase() {
        return isCreatDateBase;
    }

    public void setCreatDateBase(boolean creatDateBase) {
        isCreatDateBase = creatDateBase;
    }
}


