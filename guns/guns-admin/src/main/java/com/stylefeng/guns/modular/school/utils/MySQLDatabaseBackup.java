package com.stylefeng.guns.modular.school.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * MySQL数据库备份 
 *  
 * @author GaoHuanjie 
 */  
public class MySQLDatabaseBackup {  
    private final static Logger logger = LoggerFactory.getLogger(MySQLDatabaseBackup.class);
    /** 
     * Java代码实现MySQL数据库导出 
     *  
     * @author GaoHuanjie 
     * @param exec  mysql脚本安装目录F:\\soft\\mysql-5.5.25-win32-wys\\bin\\mysqldump
     * @param hostIP MySQL数据库所在服务器地址IP 
     * @param Port MySQL数据库所在服务器端口
     * @param userName 进入数据库所需要的用户名 
     * @param password 进入数据库所需要的密码 
     * @param savePath 数据库导出文件保存路径 
     * @param fileName 数据库导出文件文件名 
     * @param databaseName 要导出的数据库名 
     * @return 返回true表示导出成功，否则返回false。 
     */  
    public static boolean exportDatabaseTool(String exec,String hostIP,String port, String userName, String password, String savePath, String fileName, String databaseName) throws InterruptedException {  
        File saveFile = new File(savePath);  
        if (!saveFile.exists()) {// 如果目录不存在  
            saveFile.mkdirs();// 创建文件夹  
        }  
        if(!savePath.endsWith(File.separator)){  
            savePath = savePath + File.separator;  
        }  
        
        PrintWriter printWriter = null;  
        BufferedReader bufferedReader = null;  
        try {  
            
            logger.info("备份参数{}",String.format("%s -h%s -P%s -u%s -p%s --set-charset=utf8 --databases %s", exec,hostIP,port,userName,password,databaseName));
            
            printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));  
            Process process = Runtime.getRuntime().exec(String.format("%s -h%s -P%s -u%s -p%s --set-charset=utf8 --databases %s", exec,hostIP,port,userName,password,databaseName));  
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");  
            bufferedReader = new BufferedReader(inputStreamReader);  
            String line;  
            while((line = bufferedReader.readLine())!= null){  
                printWriter.println(line);  
            }  
            printWriter.flush();  
            bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "utf8"));  
            while((line = bufferedReader.readLine())!= null){  
                logger.info(line);
            }  
            
            if(process.waitFor() == 0){//0 表示线程正常终止。  
                return true;  
            }  
        }catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
                if (printWriter != null) {  
                    printWriter.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return false;  
    }  
      
    public static void main(String[] args){  
        try {  
            if (exportDatabaseTool("F:\\soft\\mysql-5.5.25-win32-wys\\bin\\mysqldump","172.16.3.216","3306", "root", "ectrip", "D:/backupDatabase", "2014-10-24.sql", "guns school_db")) {  
                System.out.println("数据库成功备份！！！");  
            } else {  
                System.out.println("数据库备份失败！！！");  
            }  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
}
