package com.stylefeng.guns.modular.school.bean;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stylefeng.guns.config.properties.BackUpProperties;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.school.utils.FtpUtil;
import com.stylefeng.guns.modular.school.utils.MySQLDatabaseBackup;

/**
 * 数据库备份
* @ClassName: BackUpDataBase  
* @Description: TODO(这里用一句话描述这个类的作用)  
* @author walden
* @date 2017年10月24日 上午10:43:26  
*
 */
@Component
public class BackUpDataBaseBean {
    private final static Logger logger = LoggerFactory.getLogger(BackUpDataBaseBean.class);
    
    @Autowired
    BackUpProperties backUpConfig;
    
    public void backUp(){

        logger.info(">>>>>>>>>>>>>数据库备份开始");
        logger.info(">>>>>>>>>>>>>数据库导出");
        try {
            boolean result = false;
            // 以日期为名进行备份
            String fileName = DateUtil.getAllTime().concat(".sql");
            result = MySQLDatabaseBackup.exportDatabaseTool(backUpConfig.getDbExec(), backUpConfig.getDbHostName(),
                    backUpConfig.getDbPort().toString(), backUpConfig.getDbUserName(), backUpConfig.getDbPassword(),
                    backUpConfig.getDbPath(), fileName, backUpConfig.getDbDatabases());
            if (result) {
                logger.info(">>>>>>>>>>>>>数据库导出成功，开始进行服务器文件上传");
                try {
                    String targetPath = backUpConfig.getDbPath().concat(fileName);
                    result = FtpUtil.upload(backUpConfig.getFtpHostName(),backUpConfig.getFtpUserName(), 
                            backUpConfig.getFtpPort(), backUpConfig.getFtpPassword(), backUpConfig.getFtpPath(),targetPath,
                             fileName);
                    if (result) {
                        logger.info(">>>>>>>>>>>>>数据库导出成功，开始进行服务器文件上传成功");
                    } else {
                        logger.info(">>>>>>>>>>>>>数据库导出成功，开始进行服务器文件上传失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.info(">>>>>>>>>>>>>FTP服务器上传异常");
                }
            } else {
                logger.info(">>>>>>>>>>>>>数据库导出失败，备份终止");
            }
        } catch (InterruptedException e) {
            logger.info(">>>>>>>>>>>>>数据库导出异常");
        }
        logger.info(">>>>>>>>>>>>>数据库结束");

        logger.info(">>>>>>>>>>>>>数据库备份结束");
    
    }
    
}
