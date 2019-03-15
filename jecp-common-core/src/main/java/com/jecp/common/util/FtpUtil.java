package com.jecp.common.util;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FtpUtil { 
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    private FTPClient ftpClient = null; //FTP 客户端代理     
    public int i = 1; //FTP状态码 

    /** 
     * 连接到服务器 
     * 
     * @return true 连接服务器成功，false 连接服务器失败 
     */ 
    public boolean connectServer(String ip,Integer port,String username,String password) { 
        boolean flag = true; 
        if (ftpClient == null) { 
            int reply; 
            try {                
                ftpClient = new FTPClient(); 
                ftpClient.setControlEncoding("GBK");                     
                ftpClient.connect(ip,port); 
                ftpClient.login(username, password);
                reply = ftpClient.getReplyCode(); 
                ftpClient.setDataTimeout(120000); 
                if (!FTPReply.isPositiveCompletion(reply)) { 
                    ftpClient.disconnect(); 
                    log.info("登录ftp服务器 " + ip + "失败，请检查用户名密码！"); 
                    flag = false; 
                } 
                i++;                 
            } catch (SocketException e) { 
                flag = false; 
                e.printStackTrace(); 
                log.info("登录ftp服务器 " + ip + " 失败,连接超时！"); 
            } catch (IOException e) { 
                flag = false; 
                e.printStackTrace(); 
                log.info("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！"); 
            } 
        } 
        return flag; 
    }
    
    /**
     * 上传文件
     * 
     * @param remoteFile
     *            远程文件路径,支持多级目录嵌套
 	 * @param localFile
     *            本地文件名称，绝对路径
     * 
     */
    public boolean uploadFile(String remoteFile, File localFile)
            throws IOException {
    	boolean flag = false;
        InputStream in = new FileInputStream(localFile);
//        String remote = new String(remoteFile.getBytes("GBK"),"iso-8859-1");
        String remote=remoteFile;
        if(ftpClient.storeFile(remote, in)){
        	flag = true;
        	log.info(localFile.getAbsolutePath()+"上传文件成功！");
        }else{
        	log.info(localFile.getAbsolutePath()+"上传文件失败！");
        }
        in.close();
        return flag;
    }
    
    /** 
     * 上传单个文件，并重命名 
     * 
     * @param localFile--本地文件路径 
     * @param localRootFile--本地文件父文件夹路径 
     * @param distFolder--新的文件名,可以命名为空"" 
     * @return true 上传成功，false 上传失败 
     * @throws IOException 
     */ 
    public boolean uploadFile(String local, String remote) throws IOException { 
        boolean flag = true; 
        String remoteFileName = remote;
        if (remote.contains("/")) {
            remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
            // 创建服务器远程目录结构，创建失败直接返回
            if (!CreateDirecroty(remote)) {
                return false;
            }
        }
        FTPFile[] files = ftpClient.listFiles(new String(remoteFileName));
        File f = new File(local);
        if(!uploadFile(remoteFileName, f)){
        	flag = false;
        }
        return flag; 
    } 

    /** 
     * 上传文件夹内的所有文件 
     * 
     * 
     * @param filename
     *       本地文件夹绝对路径
     * @param uploadpath
     * 		 上传到FTP的路径,形式为/或/dir1/dir2/../
     * @return true 上传成功，false 上传失败 
     * @throws IOException
     */ 
    public List uploadManyFile(String filename, String uploadpath) { 
            boolean flag = true; 
            List l = new ArrayList();
            StringBuffer strBuf = new StringBuffer(); 
            int n = 0; //上传失败的文件个数
            int m = 0; //上传成功的文件个数
            try { 	
            	ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
                ftpClient.enterLocalPassiveMode(); 
                ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
        		ftpClient.changeWorkingDirectory("/"); 
        		File file = new File(filename);
        		File fileList[] = file.listFiles(); 
                for (File upfile : fileList) { 
                        if (upfile.isDirectory()) {
                          uploadManyFile(upfile.getAbsoluteFile().toString(),uploadpath); 
                        } else { 
                        	String local = upfile.getCanonicalPath().replaceAll("\\\\","/");
                            String remote = uploadpath.replaceAll("\\\\","/") + local.substring(local.indexOf("/") + 1);
                            flag = uploadFile(local, remote);
                            ftpClient.changeWorkingDirectory("/");
                        } 
                        if (!flag) { 
                        	    n++;
                                strBuf.append(upfile.getName() + ","); 
                                log.info("文件［" + upfile.getName() + "］上传失败");
                        } else{
                        	m++;
                        }
                } 
                l.add(0, n);
                l.add(1, m);
                l.add(2, strBuf.toString());
            } catch (NullPointerException e) { 
                e.printStackTrace(); 
                log.info("本地文件上传失败！找不到上传文件！"); 
            } catch (Exception e) { 
                e.printStackTrace(); 
                log.info("本地文件上传失败！"); 
            } 
            return l;
    } 

    /** 
     * 下载文件 
     * 
     * @param remoteFileName             --服务器上的文件名 
     * @param localFileName--本地文件名 
     * @return true 下载成功，false 下载失败 
     */ 
    public boolean loadFile(String remoteFileName, String localFileName) { 
        boolean flag = true; 
        // 下载文件 
        BufferedOutputStream buffOut = null; 
        try { 
            buffOut = new BufferedOutputStream(new FileOutputStream(localFileName)); 
            flag = ftpClient.retrieveFile(remoteFileName, buffOut); 
        } catch (Exception e) { 
            e.printStackTrace(); 
            log.info("本地文件下载失败！"); 
        } finally { 
            try { 
                if (buffOut != null) 
                    buffOut.close(); 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
        } 
        return flag; 
    } 

    /** 
     * 删除一个文件 
     */ 
    public boolean deleteFile(String filename) { 
        boolean flag = true; 
        try { 
            flag = ftpClient.deleteFile(filename); 
            if (flag) { 
                log.info("删除文件"+filename+"成功！");
            } else { 
                log.info("删除文件"+filename+"成功！");
            } 
        } catch (IOException ioe) { 
            ioe.printStackTrace(); 
        } 
        return flag; 
    } 

    /** 
     * 删除目录 
     */ 
    public void deleteDirectory(String pathname) { 
        try { 
            File file = new File(pathname); 
            if (file.isDirectory()) { 
                File file2[] = file.listFiles(); 
            } else { 
                deleteFile(pathname); 
            } 
            ftpClient.removeDirectory(pathname); 
        } catch (IOException ioe) { 
            ioe.printStackTrace(); 
        } 
    } 

    /** 
     * 删除空目录 
     */ 
    public void deleteEmptyDirectory(String pathname) { 
        try { 
            ftpClient.removeDirectory(pathname); 
        } catch (IOException ioe) { 
            ioe.printStackTrace(); 
        } 
    } 

    /** 
     * 列出服务器上文件和目录 
     * 
     * @param regStr --匹配的正则表达式 
     */ 
    public FTPFile[] listRemoteFiles(String regStr) { 
        try { 
            FTPFile[] ftpFiles = ftpClient.listFiles(regStr); 
            return ftpFiles;
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return null;
    } 

    /** 
     * 列出Ftp服务器上的所有文件和目录 
     */ 
    public FTPFile[] listRemoteAllFiles() {         
    	FTPFile[] ftpFiles={};
		try {
			ftpFiles = ftpClient.listFiles();
		} catch (IOException e) {			
			e.printStackTrace();
			log.error("",e);
		}
        return ftpFiles;              
    } 

    /** 
     * 关闭连接 
     */ 
    public void closeConnect() { 
        try { 
            if (ftpClient != null) { 
                ftpClient.logout(); 
                ftpClient.disconnect(); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 


    /** 
     * 设置传输文件的类型[文本文件或者二进制文件] 
     * 
     * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE 
     * 
     */ 
    public void setFileType(int fileType) { 
        try { 
            ftpClient.setFileType(fileType); 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
    /** 
     * 进入到服务器的某个目录下 
     * 
     * @param directory 
     */ 
    public boolean changeWorkingDirectory(String directory) { 
		boolean flag = true;
        try { 
        	flag = ftpClient.changeWorkingDirectory(directory); 
        	if (flag) { 
                log.info("进入文件夹"+ directory + " 成功！");

        } else {  
            log.info("进入文件夹"+ directory + " 失败！");
        } 
        } catch (IOException ioe) { 
            ioe.printStackTrace(); 
        } 
        return flag;
    } 

    /** 
     * 返回到上一层目录 
     */ 
    public void changeToParentDirectory() { 
        try { 
            ftpClient.changeToParentDirectory(); 
        } catch (IOException ioe) { 
            ioe.printStackTrace(); 
        } 
    } 

    /** 
     * 重命名文件 
     * 
     * @param oldFileName --原文件名 
     * @param newFileName --新文件名 
     */ 
    public void renameFile(String oldFileName, String newFileName) { 
        try { 
            ftpClient.rename(oldFileName, newFileName); 
        } catch (IOException ioe) { 
            ioe.printStackTrace(); 
        } 
    } 

    /** 
     * 设置FTP客服端的配置--一般可以不设置 
     * 
     * @return ftpConfig 
     */ 
    private FTPClientConfig getFtpConfig() { 
        FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX); 
        ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING); 
        return ftpConfig; 
    } 

    /** 
     * 转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码 
     * 
     * @param obj 
     * @return "" 
     */ 
    private String iso8859togbk(Object obj) { 
        try { 
            if (obj == null) 
                return ""; 
            else 
                return new String(obj.toString().getBytes("iso-8859-1"), "GBK"); 
        } catch (Exception e) { 
                return ""; 
        } 
    } 

    /** 
     * 在服务器上创建一个文件夹 
     * 
     * @param dir 文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>... 
     */ 
    public boolean makeDirectory(String dir) { 
        boolean flag = true; 
        try { 
            flag = ftpClient.makeDirectory(dir); 
            if (flag) { 
                log.info("创建文件夹"+ dir + " 成功！");

            } else {  
                log.info("创建文件夹"+ dir + " 失败！");
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return flag; 
    }
    
    // 检查路径是否存在，存在返回true，否则false  
    public boolean existFile(String path) throws IOException {    
        boolean flag = false;    
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);    
       /* for (FTPFile ftpFile : ftpFileArr) {    
            if (ftpFile.isDirectory()    
                    && ftpFile.getName().equalsIgnoreCase(path)) {    
                flag = true;    
                break;    
            }    
        } */
        if(ftpFileArr.length > 0){
        	flag = true;    
        }
        return flag;    
    }  
    
    /**
     * 递归创建远程服务器目录
     * 
     * @param remote
     *            远程服务器文件绝对路径
     * 
     * @return 目录创建是否成功
     * @throws IOException
     */
     public boolean CreateDirecroty(String remote) throws IOException {
         boolean success = true;
         String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
         // 如果远程目录不存在，则递归创建远程服务器目录
         if (!directory.equalsIgnoreCase("/")&& !changeWorkingDirectory(new String(directory))) {
 			int start = 0;
             int end = 0;
             if (directory.startsWith("/")) {
                 start = 1;
             } else {
                 start = 0;
             }
             end = directory.indexOf("/", start);
             while (true) {
                 String subDirectory = new String(remote.substring(start, end).getBytes("GBK"),"iso-8859-1");
                 if (changeWorkingDirectory(subDirectory)) {
                     if (makeDirectory(subDirectory)) {
                    	 changeWorkingDirectory(subDirectory);
                     } else {
                     	log.info("创建目录["+subDirectory+"]失败");
                     	log.info("创建目录["+subDirectory+"]失败");
                         success = false;
                         return success;
                     }
                 }
                 start = end + 1;
                 end = directory.indexOf("/", start);
                 // 检查所有目录是否创建完毕
                 if (end <= start) {
                     break;
                 }
             }
         }
         return success;
    }
    public String getPwd(){
    	String pwd="";
    	try {
			String[] rt=ftpClient.doCommandAsStrings("pwd","");
			Pattern p=Pattern.compile("\"(.*?)\"");  
	        Matcher m=p.matcher(rt[0]);
	        if(m.find()){  
	            pwd=m.group(0).replace("\"","");  
	        } 
		} catch (IOException e) {			
			e.printStackTrace();
			log.error("",e);
		}
    	return pwd;
    }
    public static void main(String[] args) {
		FtpUtil ftpClient1 = new FtpUtil();
		if(ftpClient1.connectServer("10.212.21.90",21,"admin","Jna.com!2")){
			ftpClient1.setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件 
			ftpClient1.changeWorkingDirectory("/1");
			
			System.out.println(ftpClient1.getPwd());
			try {
				ftpClient1.uploadFile("C:/各业务系统重要指标运行情况(5.21-至今) - 副本.xls","测试33.p");
			} catch (IOException e) {				
				e.printStackTrace();
			} 			
			ftpClient1.closeConnect();// 关闭连接 
		}
	}
}
