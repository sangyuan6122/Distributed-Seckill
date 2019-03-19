package com.jecp.sysmanage.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.jecp.sysmanage.model.TawSystemAttachment;

public class FileUtils {
	/**
	 * 压缩文件
	 * @param files
	 * @param zipStream
	 * @param zipSource
	 * @param bufferStream
	 * @throws IOException
	 */
    public static void zipFile(String zippath,List<TawSystemAttachment> attchlist) {
    	ZipOutputStream zipStream = null;
        BufferedInputStream bufferStream = null;
        FileInputStream zipSource = null;
    	ZipEntry zipEntry; 
    	byte[] bufferArea;
    	TawSystemAttachment attach;
    	try {
			zipStream = new ZipOutputStream(new FileOutputStream(zippath));
			for(int i=0;i<attchlist.size();i++){
	    		int read = 0;
	    		bufferArea = new byte[1024 * 10];
	    		attach=attchlist.get(i);
	    		zipEntry=new ZipEntry(i+"_"+attach.getUploader()+"_"+attach.getFilecnname());	    		
				zipSource=new FileInputStream(new File(attach.getPath()+'/'+attach.getFileenname()));				
	    		zipStream.putNextEntry(zipEntry);    		
	    		bufferStream=new BufferedInputStream(zipSource, 1024 * 10);
				while((read = bufferStream.read(bufferArea, 0, 1024 * 10)) != -1)
		        {
					zipStream.write(bufferArea, 0, read);
		        }
	    	}  
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
			try {
                if(null != bufferStream) bufferStream.close();
                if(null != zipStream) zipStream.close();
                if(null != zipSource) zipSource.close();
            } catch (IOException e) {                   
                e.printStackTrace();
            }
		}
    		
    }
}
