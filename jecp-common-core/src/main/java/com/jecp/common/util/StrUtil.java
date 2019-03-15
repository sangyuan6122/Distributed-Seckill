package com.jecp.common.util;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {
	/**
	 * 获得UUID
	 * @return
	 */
	public static String getUUID(){		
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}
	/**
	 * 获取指定字符串出现次数
	 * @param str 源字符串
	 * @param findStr 查询字符串
	 * @return
	 */
	public static int appearNumber(String str,String findStr){
		int count=0;
		Pattern p=Pattern.compile(findStr);
		Matcher m=p.matcher(str);
		while(m.find()){
			count++;
		}
		return count;
	};
	public static int getStringPosition(String str,String findStr,int idx){
		int count=0;
		Pattern p=Pattern.compile(findStr);
		Matcher m=p.matcher(str);
		while(m.find()){
			count++;
//			System.out.println("---"+m.start());
			if(count==idx){
				return m.start();
			}
		}
		return count;
	}
	public static String oracleClob2Str(Clob clob)  {
		try {
			return (clob != null ? clob.getSubString(1, (int) clob.length()) : null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
