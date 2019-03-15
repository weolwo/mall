package com.mall.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 用于上传图片的ftp工具类
 */
public class FtpUtil {
    private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

    private static String ftpIp = PropertiesUtil.getPropertis("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getPropertis("ftp.user");
    private static String ftpPass = PropertiesUtil.getPropertis("ftp.pass");

    public FtpUtil(String ip, int port, String user, String pwd) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }

    public static boolean uploadFile(List<File> fileList) throws IOException {
        FtpUtil ftpUtil = new FtpUtil(ftpIp, 21, ftpUser, ftpPass);
        logger.info("开始链接ftp服务器{}");
        boolean result = ftpUtil.uploadFile("img", fileList);
        logger.info("开始链接ftp服务器,结束上传,上传结果{}", result);
        return result;
    }

    private boolean uploadFile(String remoterPath, List<File> fileList) throws IOException {
        boolean result = true;
        FileInputStream fis = null;
        if (connectServer(this.getIp(),this.getPort(),this.getUser(),this.getPwd())){
            try {
                ftpClient.changeWorkingDirectory(remoterPath);//切换
                ftpClient.setBufferSize(8192);
                ftpClient.setControlEncoding("UTF-8");//设置便编码
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//文件类型为二进制
                ftpClient.enterLocalPassiveMode();//设置为被动模式
                //开始上传文件
                for (File file : fileList) {
                    fis=new FileInputStream(file);
                    ftpClient.storeFile(file.getName(),fis);
                }
            } catch (IOException e) {
                 result = true;
                logger.error("上传文件异常",e);
            }finally {
                fis.close();//关闭流
                ftpClient.disconnect();//关闭连接
            }
        }
        return result;
    }


    private boolean connectServer(String ip, int port, String user, String pwd) {

        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.error("连接FTP服务器异常", e);
        }
        return isSuccess;
    }

    private String ip;
    private int port;
    private String user;
    private String pwd;
    private FTPClient ftpClient;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
