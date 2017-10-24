package com.stylefeng.guns.modular.school.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FtpUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
    
    /**
     * 单个文件上传
    * @Title: upload  
    * @Description: 单个文件上传
    * @param @param ftpUrl 
    * @param @param userName
    * @param @param port
    * @param @param password
    * @param @param directory
    * @param @param srcFileName
    * @param @param destName
    * @param @return
    * @param @throws IOException    设定文件  
    * @return boolean    返回类型  
    * @throws
     */
    public static boolean upload(String ftpUrl, String userName, int port, String password, String directory,
            String srcFileName, String destName) throws IOException {
        FTPClient ftpClient = new FTPClient();
        FileInputStream fis = null;
        boolean result = false;
        try {
            ftpClient.connect(ftpUrl, port);
            ftpClient.login(userName, password);
            ftpClient.enterLocalPassiveMode();
            File srcFile = new File(srcFileName);
            fis = new FileInputStream(srcFile);
            // 设置上传目录
            ftpClient.changeWorkingDirectory(directory);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("utf-8");
            // 设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            result = ftpClient.storeFile(destName, fis);
            return result;
        } catch (NumberFormatException e) {
            logger.info("FTP端口配置错误:不是数字:");
            throw e;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        } finally {
            IOUtils.closeQuietly(fis);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        upload("172.16.3.249", "user", 21, "123456", "/", "D://backupDatabase//2014-10-14.sql", "2014-10-24.sql");
    }

}
