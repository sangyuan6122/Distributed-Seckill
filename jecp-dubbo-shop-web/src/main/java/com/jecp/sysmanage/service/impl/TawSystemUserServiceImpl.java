package com.jecp.sysmanage.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jecp.base.service.impl.BaseServiceImpl;
import com.jecp.common.cache.rediscache.annotation.RedisCache;
import com.jecp.common.cache.rediscache.util.RedisDataType;
import com.jecp.common.cache.rediscache.util.RedisReturnType;
import com.jecp.common.model.Tree;
import com.jecp.common.model.TreeAttributes;
import com.jecp.common.util.StrUtil;
import com.jecp.common.util.TimeUtil;
import com.jecp.sysmanage.dao.TawSystemUserDao;
import com.jecp.sysmanage.model.TawSystemCookie;
import com.jecp.sysmanage.model.TawSystemUser;
import com.jecp.sysmanage.service.TawSystemCookieService;
import com.jecp.sysmanage.service.TawSystemDeptService;
import com.jecp.sysmanage.service.TawSystemUserService;
import com.jecp.sysmanage.util.CookieUtil;
import com.jecp.sysmanage.util.RSAUtil;
import com.jecp.sysmanage.util.SysCatchUtil;

@Service("tawSystemUserService")
public class TawSystemUserServiceImpl extends BaseServiceImpl<TawSystemUser> implements TawSystemUserService {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
	@Resource
	private TawSystemUserDao tawSystemUserDao;
	@Resource
	private TawSystemCookieService tawSystemCookieService;
	@Resource
	private TawSystemDeptService tawSystemDeptService;
	
	@PostConstruct
	private void init() {
		super.setBasedao(tawSystemUserDao);
	}

	@Override
	public String login(HttpServletResponse response, String userid, String password, HttpServletRequest request) {
		JSONObject jSONObject = new JSONObject();
		if (userid == null || password == null) {
			jSONObject.put("status", "0");
		} else {
			try {
				password = RSAUtil.decode1(password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TawSystemUser user = tawSystemUserDao.login(userid, password);
			if (user != null) {
				String remoteAddr = request.getHeader("X-Forwarded-For");
				if (!checkIP(remoteAddr)) {
					remoteAddr = request.getHeader("Proxy-Client-IP");
				}
				if (!checkIP(remoteAddr)) {
					remoteAddr = request.getHeader("WL-Proxy-Client-IP");
				}
				if (!checkIP(remoteAddr)) {
					remoteAddr = request.getRemoteAddr();
				}
				log.info("用户:" + userid + ",IP:" + remoteAddr + "登陆成功！");
				TawSystemCookie tawSystemCookie = new TawSystemCookie();
				tawSystemCookie.setCookieid(StrUtil.getUUID());
				tawSystemCookie.setUid(user.getId());
				tawSystemCookie.setDeptid(user.getDeptid());
				tawSystemCookie.setDeptname(user.getDeptname());
				tawSystemCookie.setLastoperatetime(Timestamp.valueOf(TimeUtil.getcuTime()));
				tawSystemCookie.setLoginip(remoteAddr);
				tawSystemCookie.setUserid(user.getUserid());
				tawSystemCookie.setUsername(user.getUsername());
				tawSystemCookie.setMobile(user.getMobile());
				new CookieUtil().addCookie(response, tawSystemCookie.getCookieid());
				tawSystemCookieService.addToCache(tawSystemCookie);
				jSONObject.put("status", "1");
			} else {
				jSONObject.put("status", "0");
			}
		}
		return jSONObject.toString();
	}
	
	@Override
	public String authorize(HttpServletResponse response, String userid, String password, HttpServletRequest request) {
		JSONObject jSONObject = new JSONObject();
		if (userid == null || password == null) {
			jSONObject.put("status", "0");
		} else {
			TawSystemUser user = tawSystemUserDao.login(userid, password);
			if (user != null) {
				String remoteAddr = request.getHeader("X-Forwarded-For");
				if (!checkIP(remoteAddr)) {
					remoteAddr = request.getHeader("Proxy-Client-IP");
				}
				if (!checkIP(remoteAddr)) {
					remoteAddr = request.getHeader("WL-Proxy-Client-IP");
				}
				if (!checkIP(remoteAddr)) {
					remoteAddr = request.getRemoteAddr();
				}

				TawSystemCookie tawSystemCookie = new TawSystemCookie();
				tawSystemCookie.setCookieid(StrUtil.getUUID());
				tawSystemCookie.setUid(user.getId());
				tawSystemCookie.setDeptid(user.getDeptid());
				tawSystemCookie.setDeptname(user.getDeptname());
				tawSystemCookie.setLastoperatetime(Timestamp.valueOf(TimeUtil.getcuTime()));
				tawSystemCookie.setLoginip(remoteAddr);
				tawSystemCookie.setUserid(user.getUserid());
				tawSystemCookie.setUsername(user.getUsername());
				tawSystemCookie.setMobile(user.getMobile());
				tawSystemCookieService.addToCache(tawSystemCookie);
				jSONObject.put("k", tawSystemCookie.getCookieid());
			} else {
				jSONObject.put("status", "0");
			}
		}
		return jSONObject.toString();
	}

	@Override
	public String addUser(TawSystemUser user) {
		JSONObject jSONObject = new JSONObject();
		try {
			user.setPassword(RSAUtil.encode1(user.getPassword()));
			Serializable id = tawSystemUserDao.save(user);
			SysCatchUtil.updateUserCache(user);
			jSONObject.put("status", "1");
		} catch (RemoteException e) {
			e.printStackTrace();
			log.error("", e);
			jSONObject.put("status", "0");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
			jSONObject.put("status", "0");
		}
		return jSONObject.toJSONString();
	}

	@Override
	public String delUser(String id) {
		JSONObject jSONObject = new JSONObject();
		try {
			TawSystemUser user = tawSystemUserDao.getById(id);
			if (user != null) {
				tawSystemUserDao.delete(id);
				SysCatchUtil.delUserCache(user.getUserid());
				jSONObject.put("status", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jSONObject.put("status", "0");
		}
		return jSONObject.toJSONString();
	}

	@Transactional("txManager")
	@Override
	public int updateUser(TawSystemUser user) {
		try {
			tawSystemUserDao.update(user);
			SysCatchUtil.updateUserCache(user);
			// rtxService.deleteUser(user.getUserid());
			// rtxService.addUser(user.getUserid(),
			// SysCatchUtil.getDeptCache().get(user.getDeptid()).getRtxdeptid().toString(),
			// user.getUsername(), RSAUtil.decode(user.getPassword()));
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static boolean checkIP(String ip) {
		if (ip == null || ip.length() == 0 || "unkown".equalsIgnoreCase(ip) || ip.split(".").length != 4) {
			return false;
		}
		return true;
	}

	@Override
	public List<TawSystemUser> getByDeptid(Long[] deptids) {
		return tawSystemUserDao.getByDeptid(deptids);
	}

	@Override
	public String getDeptUserTree(Long deptid) {
		Long[] deptids = { deptid };
		String deptJsonString = tawSystemDeptService.getTree(deptid,"user");
		JSONArray treeJson = JSON.parseArray(deptJsonString);
		List<TawSystemUser> userList = tawSystemUserDao.getByDeptid(deptids);

		for (TawSystemUser tawSystemUser : userList) {
			Tree tree = new Tree();
			TreeAttributes treeAttributes = new TreeAttributes();
			treeAttributes.setA1("user");
			treeAttributes.setA2(tawSystemUser.getUserid());
			tree.setId(tawSystemUser.getId());
			tree.setPid(String.valueOf(deptid));
			tree.setState("open");
			tree.setText(tawSystemUser.getUsername());
			tree.setAttributes(treeAttributes);
			tree.setIconCls("other-people");
			treeJson.add(JSONObject.toJSON(tree));
			tree = null;
			treeAttributes = null;
		}
		return treeJson.toJSONString();
	}

	@Override
	public String get(String id) {
		JSONObject jSONObject = new JSONObject();
		TawSystemUser user = tawSystemUserDao.getById(id);
		if (user != null) {
			jSONObject.put("id", user.getId());
			jSONObject.put("userid", user.getUserid());
			jSONObject.put("username", user.getUsername());
			jSONObject.put("deptid", user.getDeptid());
			jSONObject.put("deptname", user.getDeptname());
			jSONObject.put("mobile", user.getMobile());
			jSONObject.put("external", user.getExternal());
			jSONObject.put("accountLocked", user.getAccountLocked());
			jSONObject.put("failCount", user.getFailCount());
			jSONObject.put("weixinid", user.getWeixinid());
			jSONObject.put("remark", user.getRemark());
		}
		return jSONObject.toJSONString();
	}

	@Override
	@RedisCache(type = TawSystemUser.class, dataType = RedisDataType.String, returnType = RedisReturnType.Object)
	public TawSystemUser getById(Serializable id) {
		return super.getById(id);
	}

	@Override
	public String allUserCookie(HttpServletRequest request) {
		tawSystemCookieService.delAllUserCookie();
		String fileName=request.getSession().getServletContext().getRealPath(File.separator)+File.separator+"allcookie.txt";
		File file =new File(fileName);
		FileWriter fileWritter = null;
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			fileWritter = new FileWriter(file,false);
			List<TawSystemUser> list=tawSystemUserDao.findAll();
			for(TawSystemUser tawSystemUser:list) {
				TawSystemCookie tawSystemCookie=bulidCookie(tawSystemUser);
				tawSystemCookieService.addToCache(tawSystemCookie);
				fileWritter.write(tawSystemCookie.getCookieid());
				fileWritter.write(System.getProperty("line.separator"));
			}			
		}catch(Exception e) {
			log.error("",e);
		}finally {
			try {
				if(fileWritter!=null){
					fileWritter.close();
				}
			} catch (IOException e) {			
				e.printStackTrace();
			}
		}
		return fileName;
	}
	private TawSystemCookie bulidCookie(TawSystemUser user) {
		TawSystemCookie tawSystemCookie = new TawSystemCookie();
		tawSystemCookie.setCookieid(StrUtil.getUUID());
		tawSystemCookie.setUid(user.getId());
		tawSystemCookie.setDeptid(user.getDeptid());
		tawSystemCookie.setDeptname(user.getDeptname());
		tawSystemCookie.setLastoperatetime(Timestamp.valueOf(TimeUtil.getcuTime()));		
		tawSystemCookie.setUserid(user.getUserid());
		tawSystemCookie.setUsername(user.getUsername());
		tawSystemCookie.setMobile(user.getMobile());
		return tawSystemCookie;
	}
}
