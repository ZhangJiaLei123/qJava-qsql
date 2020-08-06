package com.qjava.qsql.postgresql;

import java.sql.*;

import static org.postgresql.shaded.com.ongres.scram.common.util.Preconditions.checkNotNull;

/**
 * Postgre连接器
 * @Author: Zhang.Jialei
 * @Date: 2020/8/3 11:59
 */
public class PostgreConnetionBase {

    PostgreBean postgreBean = null;
    Connection connection = null;

    public PostgreConnetionBase(PostgreBean postgreBean){
        this.postgreBean = postgreBean;
    }

    /**
     * 连接
     * @return
     */
    public boolean connect(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        String url = String.format("jdbc:postgresql://%s:%d/%s",
                postgreBean.getHost(), postgreBean.getPost(), postgreBean.getDatabase());
        String uname = postgreBean.getUname();
        String pwd = postgreBean.getPwd();

        checkNotNull(uname, "用户名不能为空");
        checkNotNull(pwd, "密码不能为空");

        try {
            connection = DriverManager.getConnection(url, uname, pwd);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询
     * @return
     */
    public ResultSet executeQuery(String sql){
        Statement stmt = null;
        try {
            // 创建语句
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;

    }

    /**
     * 更新
     * @param sql
     * @return
     */
    public int executeUpdate(String sql){

        try {
            // 创建语句
            Statement stmt = connection.createStatement();
            int rs = stmt.executeUpdate(sql);
            return rs;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return -1;
    }

    public PostgreBean getPostgreBean() {
        return postgreBean;
    }

    public void setPostgreBean(PostgreBean postgreBean) {
        this.postgreBean = postgreBean;
    }
}
