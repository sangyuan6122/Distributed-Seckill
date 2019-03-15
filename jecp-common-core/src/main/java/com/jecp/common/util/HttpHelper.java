package com.jecp.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.HttpServletRequest;

import com.jecp.common.model.ResContent;

public class HttpHelper {
	public enum ContentTypeEnum {
		JSON_UTF8("application/json;charset=UTF-8"),
		JSON_GBK("application/json;charset=GBK");
		private String name;  	   
		// 构造方法  
	    private ContentTypeEnum(String name) {  
	        this.name = name;  
	    } 
	    public String getName() {
			return name;
		}
	}
	public enum OptionsEnum {
		GET,POST,PUT,DELETE;				
	}
	
	static boolean proxySet = false;
    static String proxyHost = "127.0.0.1";
    static int proxyPort = 8087;
    /** 
     * 编码 
     * @param source 
     * @return 
     */ 
    public static String urlEncode(String source,String encode) {  
        String result = source;          
        try {  
            result = java.net.URLEncoder.encode(source,encode);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return "0";  
        }  
        return result;  
    }
    
    
    public static String urlEncodeGBK(String source) {  
        String result = source;  
        try {  
            result = java.net.URLEncoder.encode(source,"GBK");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return "0";  
        }  
        return result;  
    }
    /** 
     * 发起http请求获取返回结果 
     * @param req_url 请求地址 
     * @return 
     */ 
    public static String httpRequest(String req_url) {
        StringBuffer buffer = new StringBuffer();  
        try {  
            URL url = new URL(req_url);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
   
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            httpUrlConn.setConnectTimeout(50000);
            httpUrlConn.setReadTimeout(50000);
            httpUrlConn.setRequestMethod("GET"); 
           // httpUrlConn.setRequestProperty("Accept", "image/png,image/*;q=0.8,*/*;q=0.5");
           // httpUrlConn.setRequestProperty("Accept-Encoding", "gzip");  
           //httpUrlConn.setRequestProperty("Referer", "http://www.hao123.com/");
            httpUrlConn.connect();  //发送请求
            if (httpUrlConn.getResponseCode() == 200) {
            	
            	// 将返回的输入流转换成字符串  
                InputStream inputStream = httpUrlConn.getInputStream();  
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
       
                String str = null;  
                while ((str = bufferedReader.readLine()) != null) {  
                    buffer.append(str);  
                }  
                bufferedReader.close();  
                inputStreamReader.close();  
                // 释放资源  
                inputStream.close();  
                inputStream = null;  
                httpUrlConn.disconnect();
            	           	
            }     
   
        } catch (Exception e) {  
            System.out.println(e.getStackTrace());  
        }  
        return buffer.toString();  
    }  
       
    /** 
     * 发送http请求取得返回的输入流 
     * @param requestUrl 请求地址 
     * @return InputStream 
     */ 
    public static InputStream httpRequestIO(String requestUrl) {  
        InputStream inputStream = null;  
        try {  
            URL url = new URL(requestUrl);  
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
            httpUrlConn.connect();  
            // 获得返回的输入流  
            inputStream = httpUrlConn.getInputStream();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return inputStream;  
    }
     
     
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    public static ResContent sendPost(String url, String param,String cookie,boolean isRedirects) {
    	ResContent rsc = new ResContent();		
		HttpURLConnection conn =null;
		DataOutputStream out = null;
		BufferedReader br ;
		InputStreamReader in=null;
		StringBuffer result=new StringBuffer();		
		try{
			URL realUrl = new URL(url);
			conn =(HttpURLConnection) realUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setInstanceFollowRedirects(isRedirects);
			conn.setUseCaches(false);
			conn.setRequestProperty("Accept", "text/html, application/xhtml+xml, */*");			
			conn.setRequestProperty("Accept-Language", "zh-CN");
			conn.setRequestProperty("Cookie",cookie);
			conn.setRequestProperty("Connection", "Keep-Alive");				
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("contentType", "utf-8");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
			conn.setRequestProperty("Content-Length", String.valueOf(param.length()));
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setDoInput(true);
			conn.connect();
	
			out = new DataOutputStream(conn.getOutputStream());
			out.write(param.toString().getBytes());			
			out.flush();
			if(!isRedirects){
				rsc.setLocation(conn.getHeaderField("Location"));
			}
			StringBuffer ck=new StringBuffer();
			if(conn.getHeaderFields().get("Set-Cookie")!=null&&conn.getHeaderFields().get("Set-Cookie").size()>0){
				for(String c:conn.getHeaderFields().get("Set-Cookie")){
					ck.append(c);
				}
				rsc.setCookie(ck.toString() );
			}
			if("gzip".equals(conn.getContentEncoding())){
				br = new BufferedReader(new InputStreamReader(new GZIPInputStream(conn.getInputStream()),"utf-8") );
			}else{
				br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8") );
			}
			String line;
			while((line=br.readLine())!=null){
				result.append(line);
			}
			rsc.setContent(result);	
		}catch(Exception e){				
			e.printStackTrace();			
		}finally{
			try{
				if(out!=null)
					out.close();
				if(in!=null)
					in.close();
				if(conn!=null)
					conn.disconnect();
				conn=null;
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
		return rsc;
    }    
    public static String restInvoking(String url, String param,String cookie,ContentTypeEnum contentType,OptionsEnum options) {
    	HttpURLConnection conn =null;
		DataOutputStream out = null;
		BufferedReader br ;		
		StringBuffer result=new StringBuffer();		
		try{
			URL realUrl = new URL(url);
			conn =(HttpURLConnection) realUrl.openConnection();
			conn.setConnectTimeout(30000); //设置连接主机超时（单位：毫秒）  
			conn.setReadTimeout(30000);//设置从主机读取数据超时（单位：毫秒）
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");			
			conn.setUseCaches(false);
//			conn.setRequestProperty("Accept","application/json");						
			conn.setRequestProperty("Cookie",cookie);							
			conn.setRequestProperty("Content-Type", contentType.getName());	
			conn.setRequestProperty("contentType", "utf-8");

//			conn.setRequestProperty("Content-Length", String.valueOf(param.length()));
			conn.setRequestProperty("Cache-Control", "no-cache");
			conn.setRequestProperty("User-Agent", "interface");			
			conn.connect();
			out = new DataOutputStream(conn.getOutputStream());
			out.write(param.toString().getBytes());			
			out.flush();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8") );
			String line;
			while((line=br.readLine())!=null){
				result.append(line);
			}
		}catch(Exception e){				
			e.printStackTrace();			
		}finally{
			try{
				if(out!=null)
					out.close();				
				if(conn!=null)
					conn.disconnect();				
			}catch(IOException ex){
				ex.printStackTrace();
			}
			return result.toString();
		}
    }
    public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
    public static void main(String[] args) {
//        
//    	String s=restInvoking("http://21.76.122.194:18080/ds/schedmanage/monitor/datamon",
//    			"[{\"dkey\":\"111\"},{\"dkey\":\"111\"}]",
//    			"JSESSIONID=3E2736645727D9D0029B25C5C644F376; k=1408e47754cffdb74b396821898809d8",
//    			ContentTypeEnum.JSON,OptionsEnum.GET);
    	String s=sendGet("http://127.0.0.1:8080/dgn/userauth","userid=admin&password=Jna.com!2");
    	s="http://21.76.122.194:18080/ds/schedmanage/monitor/datamon";
    	restInvoking(s,"的","JSESSIONID=C84F26710BF5315D72DD734AF3C9F833; k=7505b9c72ab4aa94b1a4ed7b207b67fb",
    			ContentTypeEnum.JSON_UTF8,OptionsEnum.POST);
        System.out.println(s);

    	
    }

}
