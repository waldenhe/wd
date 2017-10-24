package com.stylefeng.guns.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * backup配置(如果需要配置别的配置可参照这个形式自己添加)
 *
 * @author walden
 * @date 2017年10月24日 10:30:37
 */
@Configuration
@ConfigurationProperties(prefix = BackUpProperties.BACKUPCONF_PREFIX)
public class BackUpProperties {

    public static final String BACKUPCONF_PREFIX = "backup";


    // ftp用户名
    private String ftpUserName;

    // ftp密码
    private String ftpPassword;

    // ftp host
    private String ftpHostName;

    // ftp端口
    private Integer ftpPort;

    // ftp上传目录
    private String ftpPath = "/";

    // 数据库脚本执行目录
    private String dbExec;

    // 数据库用户名
    private String dbUserName;

    // 数据库密码
    private String dbPassword;

    // 数据库host
    private String dbHostName;

    // 数据库端口
    private Integer dbPort;

    // 数据库备份文件保存路径
    private String dbPath;

    // 数据库要备份的库
    private String dbDatabases;

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpHostName() {
        return ftpHostName;
    }

    public void setFtpHostName(String ftpHostName) {
        this.ftpHostName = ftpHostName;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public String getDbExec() {
        return dbExec;
    }

    public void setDbExec(String dbExec) {
        this.dbExec = dbExec;
    }

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbHostName() {
        return dbHostName;
    }

    public void setDbHostName(String dbHostName) {
        this.dbHostName = dbHostName;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public void setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public String getDbDatabases() {
        return dbDatabases;
    }

    public void setDbDatabases(String dbDatabases) {
        this.dbDatabases = dbDatabases;
    }
   
}
