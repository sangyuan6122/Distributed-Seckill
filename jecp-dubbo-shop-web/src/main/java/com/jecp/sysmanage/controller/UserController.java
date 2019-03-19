package com.jecp.sysmanage.controller;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jecp.base.controller.BaseController;
import com.jecp.common.util.PasswdUtil;
import com.jecp.sysmanage.dao.TawSystemUserDao;
import com.jecp.sysmanage.model.TawSystemCookie;
import com.jecp.sysmanage.model.TawSystemUser;
import com.jecp.sysmanage.service.TawSystemCookieService;
import com.jecp.sysmanage.service.TawSystemUserService;
import com.jecp.sysmanage.util.RSAUtil;

@Controller
@Scope("prototype")
public class UserController extends BaseController{
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemUserService tawSystemUserService;
	@Resource
	private TawSystemCookieService tawSystemCookieService;
	@Resource
	private TawSystemUserDao tawSystemUserDao;	

	/**
	 * 系统登陆
	 * @return
	 */
	@RequestMapping("/userlogin")
	@ResponseBody
	public String login(String userid,String password){		
		String result=tawSystemUserService.login(super.getResponse(),userid,password, super.getRequest());
		return result;
	}
	@RequestMapping(value="/userauth",method=RequestMethod.GET)
	@ResponseBody
	public  String userauth(String userid,String password){
		String session=tawSystemUserService.authorize(super.getResponse(), userid, password, super.getRequest());
	    return session;
	}
	/**
	 * 注销 
	 */
	@RequestMapping("/userlogout")
	@ResponseBody
	public String logout(){	
		TawSystemCookie tawSystemCookie=(com.jecp.sysmanage.model.TawSystemCookie) super.getRequest().getAttribute("tawSystemCookie");
		String result=tawSystemCookieService.removeCookie(tawSystemCookie.getCookieid());
		return result;
	}
	/**
	 * 获得人员树
	 * @return
	 */
	@RequestMapping("/usertree")
	@ResponseBody
	public String tree(@RequestParam(value="deptid") Long deptid){						
		String result = tawSystemUserService.getDeptUserTree(deptid);		
		return result;
	}
	/**
	 * 获得人员对象
	 * @return
	 */
	@RequestMapping("/userget")
	@ResponseBody
	public String get(String id){
		String result = tawSystemUserService.get(id);
		return result;
	}
	/**
	 * 根据cookie获得人员信息
	 */
	@RequestMapping("/usergetByCookie")
	@ResponseBody
	public String getByCookie(){	
		String result = tawSystemUserService.get(super.getRequest().getAttribute("uid").toString());
		return result;
	}
	/**
	 * 获得cookie对象
	 * @return
	 */
	@RequestMapping("/usergetCookie")
	@ResponseBody
	public String getCookie(){
		TawSystemCookie tawSystemCookie=(TawSystemCookie) super.getRequest().getAttribute("tawSystemCookie");
		String serverIp=super.getRequest().getLocalAddr();
		JSONObject jSONObject=new JSONObject();
		if(tawSystemCookie!=null){
			tawSystemCookie.setUsercount(tawSystemCookieService.onlineTotal());
			tawSystemCookie.setServerip(serverIp);
			jSONObject=(JSONObject) JSONObject.toJSON(tawSystemCookie);						
		}else {
			jSONObject.put("status", "0");
		}
		return jSONObject.toJSONString();
	}
	/**
	 * 获得在线用户
	 */
	@RequestMapping("/useronline")
	@ResponseBody
	public String online(){
		String json=tawSystemCookieService.online();
		return json;
	}
	/**
	 * 根据ID删除人员
	 * @return
	 */
	@RequestMapping("/userdel")
	@ResponseBody
	public String del(String id){
		String json=tawSystemUserService.delUser(id);		
		return json;
	}
	/**
	 * 添加人员对象
	 * @return
	 */
	@RequestMapping("/useradd")
	@ResponseBody
	public String add(TawSystemUser tawSystemUser){
		String json=tawSystemUserService.addUser(tawSystemUser);
		return json;
	}
	/**
	 * 更新人员信息(管理员操作)
	 * @return
	 */
	@RequestMapping("/userupdate")
	@ResponseBody
	public String update(TawSystemUser tawSystemUser){
		JSONObject jSONObject=new JSONObject();
		try{
			if(!"".equals(tawSystemUser.getPassword()) &&tawSystemUser.getPassword()!=null){
				tawSystemUser.setPassword(PasswdUtil.userPwdEncode(tawSystemUser.getPassword()));								
			}else{
				TawSystemUser user = tawSystemUserDao.getById(tawSystemUser.getId());
				tawSystemUser.setPassword(user.getPassword());				
			}
			tawSystemUserService.updateUser(tawSystemUser);
			jSONObject.put("status", "1");									
		}catch(Exception e){
			jSONObject.put("status", "0");
		}	
		return jSONObject.toJSONString();
	}
	/**
	 * 用户修改密码
	 */
	@RequestMapping("/userupdatePwd")
	@ResponseBody
	public String updatePwd(String uid,String oldpassword,String newpassword2){	
		JSONObject jSONObject=new JSONObject();
		try {
			TawSystemUser user = tawSystemUserDao.getById(uid);
			String password=RSAUtil.decode1(user.getPassword());
			if(password.equals(oldpassword)){
				user.setPassword(RSAUtil.encode1(newpassword2));
				tawSystemUserService.updateUser(user);
				jSONObject.put("status", "1");
			}else{
				jSONObject.put("status", "0");
				jSONObject.put("msg", "您输入的旧密码不正确");
			}
		} catch (Exception e) {		
			log.error("",e);
			jSONObject.put("status", "0");
			jSONObject.put("msg", "其它错误");
		}
		return jSONObject.toJSONString();
	}
	/**
	 * 获得密码明文
	 * @return
	 */
	@RequestMapping("/usergetPwd")
	@ResponseBody
	public String getPwd(String id){
		JSONObject jSONObject=new JSONObject();
		TawSystemUser user = tawSystemUserDao.getById(id);
		try {
			jSONObject.put("pwd", RSAUtil.decode1(user.getPassword()));
		} catch (Exception e) {
			jSONObject.put("status", "0");
			log.error("",e);
		}
		return jSONObject.toJSONString();
	}
	@RequestMapping("/userBuildAllCookies")
	@ResponseBody
	public void buildAllCookies(){
		String fileName=tawSystemUserService.allUserCookie(super.getRequest());
		super.getResponse().setCharacterEncoding("utf-8");
		super.getResponse().setContentType("multipart/form-data");
		super.getResponse().setHeader("Content-Disposition", "attachment;fileName=cookies.txt");
		InputStream inputStream=null;
		OutputStream os=null;
		try {
			inputStream = new FileInputStream(fileName);
	        os = super.getResponse().getOutputStream();	      
	        byte[] b = new byte[2048];
	        int length;
	        while ((length = inputStream.read(b)) > 0) {
	            os.write(b, 0, length);           
	        }
		}catch(Exception e) {
			log.error("",e);
		}finally {
			if(os!=null) {
				try {
					os.close();
				} catch (IOException e) {
					log.error("",e);
				}
			}
	        if(inputStream!=null) {
	        	try {
					inputStream.close();
				} catch (IOException e) {
					log.error("",e);
				}
	        }
	        
		}
	}
	
}
