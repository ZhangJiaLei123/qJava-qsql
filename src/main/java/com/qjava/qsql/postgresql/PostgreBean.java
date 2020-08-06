package com.qjava.qsql.postgresql;

/**
 * Postgre数据库链接属性
 * @Author: Zhang.Jialei
 * @Date: 2020/8/3 11:58
 */
public class PostgreBean {


    String host = null;
    int post;
    String uname = null;
    String pwd = null;
    String database = null;


    public PostgreBean(String host, int post, String databas, String uname, String pwd) {
        this.host = host;
        this.post = post;
        this.uname = uname;
        this.pwd = pwd;
        this.database = databas;
    }

    @Override
    public String toString() {
        return "PostgreBean{" +
                "host='" + host + '\'' +
                ", post=" + post +
                ", uname='" + uname + '\'' +
                ", pwd='" + pwd + '\'' +
                ", database='" + database + '\'' +
                '}';
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
